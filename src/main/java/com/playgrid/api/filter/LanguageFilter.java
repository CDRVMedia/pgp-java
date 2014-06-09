package com.playgrid.api.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

import com.playgrid.api.client.RestConfig;

public class LanguageFilter implements ClientRequestFilter {
	private RestConfig config;

	public LanguageFilter(RestConfig config) {
		this.config = config;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		
		String locale = config.getLocale();
		requestContext.getHeaders().add(HttpHeaders.ACCEPT_LANGUAGE, locale);

	}

}
