package io.felipepoliveira.senaikeep.ws.rest.v1.interceptors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.felipepoliveira.senaikeep.ws.rest.v1.dao.JwtAuthClientDAO;
import io.felipepoliveira.senaikeep.ws.rest.v1.models.JwtAuthClientModel;
import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClient;

@Component
public class JwtAuthorizationInterceptor implements AuthenticationVerifier{
	
	@Autowired
	private JwtAuthClientDAO jwtAuthClientDAO;

	@Override
	public AuthClient verifyWithRequest(HttpServletRequest request) {
		//Get the token from request header and put in model
		String token = request.getHeader("Authorization").split("Bearer")[1].trim();
		JwtAuthClientModel jwtAuthClient = new JwtAuthClientModel();
		jwtAuthClient.setToken(token);
		
		
		try {
			
			//If the email does not exists...
			JwtAuthClientModel authenticatedClient = jwtAuthClientDAO.authenticate(jwtAuthClient);
			
			if(authenticatedClient == null) {
				System.err.println("Authorization failed");
				return null;
			}
			
			//Register the auth client in authentication delegator
			return authenticatedClient;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}

}
