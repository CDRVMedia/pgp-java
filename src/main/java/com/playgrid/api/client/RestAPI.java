package com.playgrid.api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.apache.connector.ApacheConnector;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.GZipEncoder;

import com.playgrid.api.entity.APIRoot;
import com.playgrid.api.entity.Games;
import com.playgrid.api.entity.Players;
import com.playgrid.api.filter.AuthorizationFilter;
import com.playgrid.api.filter.MediaTypeFilter;





public class RestAPI {
	
	private String PGP_URL = "http://www.playgrid.com";                         // TODO: (JP) Turn into URI
	private String PGP_VERSION = "1.1";
	private String ROOT_API_URI;
	
	private Client client;
	private WebTarget root_api_wt;


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
        clientConfig.register(MediaTypeFilter.class);                           // Register PGP MediaType filter
        clientConfig.register(GZipEncoder.class);                               // Register GZip intercepter
        clientConfig.register(LoggingFilter.class);                             // Add logging filter // TODO: (JP) integrate with log4j and DEBUG settings
        
        clientConfig.connector(new ApacheConnector(clientConfig));              // Use Apache Connector

        // TODO: (JP) Set connection timeout to 15s - only in non debug mode
        // TODO: (JP) Set read timeout to 30s to correspond to Heroku timeout - only in non debug mode
        
        client = ClientBuilder.newClient(clientConfig);                         // Create client
        
        root_api_wt = client.target(ROOT_API_URI);
        		
	}
	
	
	
	// Root
	public APIRoot getAPIRoot() {
		return root_api_wt.request().get(APIRoot.class);
	}
	
	
	
	// Games
	public Games getGames() {
		return  root_api_wt.path("games/").request().get(Games.class);
	}

	
	
	// Players
	public Players getPlayers() {
		return root_api_wt.path("players/").request().get(Players.class);
	}
	
}
