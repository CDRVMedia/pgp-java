package com.playgrid.api.client;

import javax.ws.rs.core.MediaType;

import com.playgrid.api.contextresolver.PGPContextResolver;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;




public class RestAPI {
	
	private String AUTH_TOKEN;	
	private String PGP_URL = "http://www.playgrid.com";
	private String PGP_VERSION = "v1";
	private String BASE_URI;
	
	private Client client;
	private WebResource base_wr;


	public RestAPI(String auth_token) {
		this(auth_token, null);
	}

	
	public RestAPI(String auth_token, String url) {
		this(auth_token, url, null);
	}

	
	public RestAPI(String auth_token, String url, String version) {
		
		AUTH_TOKEN = auth_token;
        PGP_URL = url != null ? url : PGP_URL;
        PGP_VERSION = version != null ? version : PGP_VERSION;
        BASE_URI = String.format("%s/api/%s", PGP_URL, PGP_VERSION);
        
        ClientConfig cc = new DefaultClientConfig();							// Create client configuration
        cc.getClasses().add(PGPContextResolver.class);

        // TODO: (JP) Set follow redirects
        
        client = Client.create(cc);												// Create client
        client.addFilter(new GZIPContentEncodingFilter());						// Add GZIP filter
        client.addFilter(new LoggingFilter());									// Add logging filter // TODO: (JP) integrate with log4j and DEBUG settings			
        
        base_wr = client.resource(BASE_URI);
        		
	}
	
	
	
	
	private Builder buildWebResource(String path) {
		
		WebResource resource = base_wr.path(path);								// Create a new WebResource
		Builder builder	= resource.accept(MediaType.APPLICATION_JSON_TYPE);		//  Accept JSON media type
		
		String token = String.format("pgp %s", AUTH_TOKEN);
		builder = builder.header("AUTHORIZATION", token);						// Add authorization header
		
		return builder;
	}
	
	
	
	public ClientResponse getGames() {
		return  buildWebResource("games/").get(ClientResponse.class);
	}
	
}
