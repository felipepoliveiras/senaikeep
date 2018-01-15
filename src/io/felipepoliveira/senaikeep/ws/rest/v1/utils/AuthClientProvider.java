package io.felipepoliveira.senaikeep.ws.rest.v1.utils;

import io.felipepoliveira.senaikeep.excp.UnauthorizedException;

public interface AuthClientProvider {
	
	public AuthClient getAuthClient();
	
	public default boolean verifyAuthClient() throws UnauthorizedException{
		if(getAuthClient() == null) {
			throw new UnauthorizedException("The authenticated client was not found");
		}else {
			return true;
		}
	}
}
