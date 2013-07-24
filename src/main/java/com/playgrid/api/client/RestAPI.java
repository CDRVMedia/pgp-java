package com.playgrid.api.client;

import java.net.URI;
import java.util.ArrayList;

import javax.naming.ConfigurationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheConnector;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.GZipEncoder;

import com.playgrid.api.client.manager.GameManager;
import com.playgrid.api.client.manager.PlayerManager;
import com.playgrid.api.entity.APIRoot;
import com.playgrid.api.entity.Base;
import com.playgrid.api.entity.Method;
import com.playgrid.api.filter.AuthorizationFilter;
import com.playgrid.api.filter.MediaTypeFilter;
import com.playgrid.api.filter.UserAgentFilter;



public class RestAPI {
	
	private volatile static RestAPI uniqueInstance;
	private static RestConfig config = new RestConfig();
	private Client client;
	private WebTarget root_api_wt;
	private ArrayList<Method> methods;


	
	private RestAPI() {
		
		String token =  null;
        try {
        	token = config.getAccessToken();
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}

        
        ClientConfig clientConfig = new ClientConfig();                         // Create client configuration

        clientConfig.register(new AuthorizationFilter(token));	 				// Register PGP Authorization Token filter
        clientConfig.register(UserAgentFilter.class);                           // Register PGP UserAgent filter
        clientConfig.register(MediaTypeFilter.class);                           // Register PGP MediaType filter
        clientConfig.register(GZipEncoder.class);                               // Register GZip intercepter
        clientConfig.register(LoggingFilter.class);                             // Add logging filter // TODO: (JP) integrate with log4j and DEBUG settings
        
        ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        clientConfig.property("jersey.config.apache.client.connectionManager", connectionManager);
        
        clientConfig.connector(new ApacheConnector(clientConfig));              // Use Apache Connector

        // TODO: (JP) Set connection timeout to 15s - only in non debug mode
        // TODO: (JP) Set read timeout to 30s to correspond to Heroku timeout - only in non debug mode
        
        client = ClientBuilder.newClient(clientConfig);                         // Create client
        
        root_api_wt = client.target(config.getAPI_URI());
        		
	}
	
	
	
	public static RestConfig getConfig() {
		return config;
	}

	
	public static RestAPI getInstance() {
		if (uniqueInstance == null) {
			synchronized (RestAPI.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new RestAPI();
				}
				
			}
		}
		return uniqueInstance;
	}

	
	public WebTarget createTarget(String url) {
		return client.target(url);
	}

	public WebTarget createTarget(URI uri) {
		return client.target(uri);
	}

	
	
	// Root
	protected APIRoot getAPIRoot() {
		return root_api_wt.request().get(APIRoot.class);
	}
	

	private Method getMethod(String name) throws Exception {
		if (methods == null) {
			Base base = this.getAPIRoot();
			this.methods = base.methods;
		}
		
		for (Method m : methods) {
			if (m.name.equals(name)) {
				return m;
			}
		}
		throw new Exception("Method Not Found");
	}

	
	
	public GameManager getGamesManager() { 
		try {
			Method gamesMethod = this.getMethod("games");
			WebTarget target = this.createTarget(gamesMethod.url);
			return new GameManager(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public PlayerManager getPlayerManager() {
		try {
			Method playersMethod = this.getMethod("players");
			WebTarget target = this.createTarget(playersMethod.url);
			return new PlayerManager(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
