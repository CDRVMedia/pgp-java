package com.playgrid.api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.apache.connector.ApacheConnector;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.GZipEncoder;

import com.playgrid.api.entity.GameListResponse;
import com.playgrid.api.filter.AuthorizationFilter;

//import com.playgrid.api.contextresolver.PGPContextResolver;




public class RestAPI {
	
	private String PGP_URL = "http://www.playgrid.com";
	private String PGP_VERSION = "v1";
	private String ROOT_API_URI;
	
	private Client client;
	private WebTarget base_wt;


	public RestAPI(String auth_token) {
		this(auth_token, null);
	}

	
	public RestAPI(String auth_token, String url) {
		this(auth_token, url, null);
	}

	
	public RestAPI(String auth_token, String url, String version) {
		
        PGP_URL = url != null ? url : PGP_URL;
        PGP_VERSION = version != null ? version : PGP_VERSION;
        
        ROOT_API_URI = String.format("%s/api/%s", PGP_URL, PGP_VERSION);
        
        ClientConfig clientConfig = new ClientConfig();                         // Create client configuration
        
        clientConfig.register(new AuthorizationFilter(auth_token));             // Register PGP Authorization Token filter
        clientConfig.register(GZipEncoder.class);                               // Register GZip intercepter
        clientConfig.register(LoggingFilter.class);                             // Add logging filter // TODO: (JP) integrate with log4j and DEBUG settings
        
        clientConfig.connector(new ApacheConnector(clientConfig));              // Use Apache Connector

        // TODO: (JP) Set connection timeout to 15s - only in non debug mode
        // TODO: (JP) Set read timeout to 30s to correspond to Heroku timeout - only in non debug mode
        
        client = ClientBuilder.newClient(clientConfig);                         // Create client
        
        base_wt = client.target(ROOT_API_URI);
        		
	}
	
	private Invocation.Builder buildWebTarget(String path) {
		Invocation.Builder builder;
		
		WebTarget target = base_wt.path(path);                                  // Create a new WebTarget
		builder = target.request(MediaType.APPLICATION_JSON_TYPE);              // Request JSON media type
		
		return builder;
	}
	
	
	
	public GameListResponse getGames() {
		return  buildWebTarget("games/").get(GameListResponse.class);
	}
	
}
