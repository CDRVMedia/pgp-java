package com.playgrid.api.client;

import java.net.URI;
import java.util.List;

import javax.naming.ConfigurationException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheConnector;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
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
	private List<Method> methods;


	
	private RestAPI() {
		
		String token =  null;                                                   // FIXME (JP): Handle this better
        try {
        	token = config.getAccessToken();
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}

        
        ClientConfig clientConfig = new ClientConfig();                         // Create client configuration

        clientConfig.register(new AuthorizationFilter(token));	 				// Register PGP Authorization Token filter
        clientConfig.register(new UserAgentFilter(config.getUserAgent()));      // Register PGP UserAgent filter
        clientConfig.register(MediaTypeFilter.class);                           // Register PGP MediaType filter
        clientConfig.register(GZipEncoder.class);                               // Register GZip intercepter
        
        if (config.isDebug()) {
        	clientConfig.register(LoggingFilter.class);                         // Add logging filter
        }
        
        ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        clientConfig.property("jersey.config.apache.client.connectionManager", connectionManager);
        
        clientConfig.connector(new ApacheConnector(clientConfig));              // Use Apache Connector

        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 30000);         // 30s in ms
        clientConfig.property(ClientProperties.READ_TIMEOUT, 30000);            // 30s in ms
        
        
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
	

	private Method getMethod(String name) {
		if (methods == null) {
			Base base = this.getAPIRoot();
			this.methods = base.methods;
		}
		
		for (Method m : methods) {
			if (m.name.equals(name)) {
				return m;
			}
		}
	
		String msg = String.format("Method '%s' Not Found", name);
		throw new NotAllowedException(msg, methods.toString(), (String[])null);
	
	}

	
	
	public GameManager getGamesManager() { 

		Method gamesMethod = this.getMethod("games");
		WebTarget target = this.createTarget(gamesMethod.url);
		return new GameManager(target);
		
	}



	public PlayerManager getPlayerManager() {
	
		Method playersMethod = this.getMethod("players");
		WebTarget target = this.createTarget(playersMethod.url);
		return new PlayerManager(target);

	}
	
}
