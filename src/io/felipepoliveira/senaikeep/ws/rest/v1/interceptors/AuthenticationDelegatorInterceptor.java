package io.felipepoliveira.senaikeep.ws.rest.v1.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClient;
import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClientProvider;

public class AuthenticationDelegatorInterceptor implements HandlerInterceptor, AuthClientProvider {
	
	private AuthClient authClient;
	
	@Autowired
	private JwtAuthorizationInterceptor jwtAuthorizationInterceptor;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("Entering authentication delegator interpcetor....");
		
		if(isException(request)) {
			return true;
		}
		
		//Check if the request contains Authorization header. If the request does not sent the header deny access
		String authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null) {
			
			System.out.println("Checking Authorization header");
			
			//Check the authorization method. If the method is not recognized deny access
			AuthClient client = null;
			if(authorizationHeader.startsWith("Bearer")) {
				client = jwtAuthorizationInterceptor.verifyWithRequest(request);
			}else{
				response.setStatus(417);
				response.setHeader("X-Status-Reason", "Authorization method was not recognized by the server");
				return false;
			}
		
			//If the client is null the authentication has failed
			if(client == null) {
				response.setStatus(401);
				response.setHeader("X-Status-Reason", "Authentication failed by the authorization method used or values passed");
				return false;
			}
			
			//If the client exists
			System.out.println("The client: " + client);
			setAuthClient(client);
			System.out.println("Authorization ok: " + client.getId());
			return true;
		}else {
			response.setHeader("X-Status-Reason", "Authorization header value was not sent by the client");
			response.setStatus(417); 
			return false;
		}
	}
	
	protected boolean isException(HttpServletRequest request) {
		//Create exception to (*/auth [POST])
		if(	request.getRequestURI().contains("auth") && 
			request.getMethod().toUpperCase().equals("POST")) {
			return true;
		} 
		//Create exception to (*/user [POST])
		else if(	request.getRequestURI().endsWith("user") && 
			request.getMethod().toUpperCase().equals("POST")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void setAuthClient(AuthClient authClient){
		this.authClient = authClient;
	}

	@Override
	public AuthClient getAuthClient() {
		return this.authClient;
	}

}
