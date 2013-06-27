package com.playgrid.api.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

public class AuthorizationFilter implements ClientRequestFilter {
	
	private final String token;

	/**
	 * @param token
	 */
	public AuthorizationFilter(String token) {

		this.token = String.format("pgp %s", token);
	
	}


	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
	
		requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, this.token);
		
	}

}
