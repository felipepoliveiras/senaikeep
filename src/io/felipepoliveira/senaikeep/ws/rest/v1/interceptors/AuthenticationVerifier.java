package io.felipepoliveira.senaikeep.ws.rest.v1.interceptors;

import javax.servlet.http.HttpServletRequest;

import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClient;

public interface AuthenticationVerifier {
	
	public AuthClient verifyWithRequest(HttpServletRequest request);
	
}
