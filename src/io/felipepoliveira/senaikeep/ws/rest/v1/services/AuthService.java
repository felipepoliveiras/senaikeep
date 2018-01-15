package io.felipepoliveira.senaikeep.ws.rest.v1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import io.felipepoliveira.senaikeep.dao.UserDAO;
import io.felipepoliveira.senaikeep.excp.UnauthorizedException;
import io.felipepoliveira.senaikeep.excp.ValidationException;
import io.felipepoliveira.senaikeep.models.UserModel;
import io.felipepoliveira.senaikeep.services.AbstractService;
import io.felipepoliveira.senaikeep.ws.rest.v1.dao.JwtAuthClientDAO;
import io.felipepoliveira.senaikeep.ws.rest.v1.models.JwtAuthClientModel;
import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClient;

@Service
public class AuthService extends AbstractService{
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private JwtAuthClientDAO jwtAuthClientDAO;
	
	public JwtAuthClientModel authenticateWithJWT(UserModel user, BindingResult bindingResult) throws ValidationException, UnauthorizedException, Exception{
		this.validate(bindingResult, "email", "password");
		
		//Search user by email and password and verify if authenticated
		user.hashPassword();
		UserModel authenticatedUser = userDAO.searchByEmailAndPassword(user.getEmail(), user.getPassword());
		if(authenticatedUser == null) {
			throw new UnauthorizedException();
		}

		//Search the jwt client date in database. If the database does not contains date about client auth persist in database0
		JwtAuthClientModel jwtAuthClient = jwtAuthClientDAO.search(authenticatedUser.getId());
		if(jwtAuthClient == null) { 
			jwtAuthClient = new JwtAuthClientModel();
			jwtAuthClient.setId(authenticatedUser.getId());
			jwtAuthClient.updateMinimumRequiredDate();
			jwtAuthClientDAO.persist(jwtAuthClient);
		}
		
		//Generate the token and return it
		jwtAuthClient.generateToken(user);
		
		return jwtAuthClient;
	}
	
	public void renewJwtMinimumRequiredDate(AuthClient authClient) {
		JwtAuthClientModel jwtAuthClient = jwtAuthClientDAO.search(authClient.getId());
		jwtAuthClient.updateMinimumRequiredDate();
		jwtAuthClientDAO.update(jwtAuthClient);
	}
	
}
