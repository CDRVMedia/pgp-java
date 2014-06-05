package com.playgrid.api.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

public class LanguageFilter implements ClientRequestFilter {
	private final String locale;

	/**
	 * @param locale code
	 */
	public LanguageFilter(String locale) {

		this.locale = locale;
	
	}
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		
		requestContext.getHeaders().add(HttpHeaders.ACCEPT_LANGUAGE, this.locale);

	}

}
