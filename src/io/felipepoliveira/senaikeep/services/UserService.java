package io.felipepoliveira.senaikeep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import io.felipepoliveira.senaikeep.dao.UserDAO;
import io.felipepoliveira.senaikeep.excp.ConflictException;
import io.felipepoliveira.senaikeep.excp.ValidationException;
import io.felipepoliveira.senaikeep.models.UserModel;

@Service
public class UserService extends AbstractService{
	
	@Autowired
	private UserDAO userDAO; 
	
	public UserModel register(UserModel user, BindingResult bindingResult) throws ValidationException, ConflictException {
		this.validate(bindingResult);
		
		//Check if email is already in use
		if(!userDAO.searchByEmail(user.getEmail()).isEmpty()) {
			bindingResult
			.addError(new FieldError("user", "email", "O email \"" + user.getEmail() + "\" já esta sendo utilizado"));
			
			throw new ConflictException();
		}
		
		//Hash user password
		user.hashPassword();
		
		userDAO.persist(user);
		
		return user;
	}
}
