package com.playgrid.api.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;



public class MediaTypeFilter implements ClientRequestFilter {

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {

		requestContext.getHeaders().add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_TYPE);

	}

}
