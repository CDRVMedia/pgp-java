package com.playgrid.api.client;

import javax.naming.ConfigurationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.apache.connector.ApacheConnector;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.GZipEncoder;

import com.playgrid.api.entity.APIRoot;
import com.playgrid.api.entity.GameResponse;
import com.playgrid.api.entity.Games;
import com.playgrid.api.entity.PlayerResponse;
import com.playgrid.api.entity.Players;
import com.playgrid.api.filter.AuthorizationFilter;
import com.playgrid.api.filter.MediaTypeFilter;
import com.playgrid.api.filter.UserAgentFilter;





public class RestAPI {
	
	private volatile static RestAPI uniqueInstance;
	private static RestConfig config = new RestConfig();
	private Client client;
	private WebTarget root_api_wt;

	
	
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
	
	
	
	// Root
	public APIRoot getAPIRoot() {
		return root_api_wt.request().get(APIRoot.class);
	}
	
	
	
	// Games
	public Games getGames() {
		return  root_api_wt.path("games/").request().get(Games.class);
	}


	public GameResponse getGame(Integer id) {
		return  root_api_wt.path(String.format("games/%s/", id)).request().get(GameResponse.class);
	}

	
	public GameResponse gameConnect() {
		return  root_api_wt.path("games/connect/").request().get(GameResponse.class);
	}

	
	public GameResponse gameDisconnect() {
		return  root_api_wt.path("games/disconnect/").request().get(GameResponse.class);
	}
	
	
	public GameResponse gameHeartbeat() {
		return  root_api_wt.path("games/heartbeat/").request().get(GameResponse.class);
	}
	
	
	
	// Players
	public Players getPlayers() {
		return root_api_wt.path("players/").request().get(Players.class);
	}

	
	public PlayerResponse playersGet(String player_token) {
		return  root_api_wt.path(String.format("players/get/%s/", player_token)).request().get(PlayerResponse.class);
	}

	
	public PlayerResponse playersGet_or_Create(String player_token) {
		return  root_api_wt.path(String.format("players/get_or_create/%s/", player_token)).request().get(PlayerResponse.class);
	}
	
	
	public PlayerResponse getPlayer(Integer id) {
		return  root_api_wt.path(String.format("players/%s/", id)).request().get(PlayerResponse.class);
	}
	
	
	public PlayerResponse playerJoin(Integer id) {
		return  root_api_wt.path(String.format("players/%s/join/", id)).request().get(PlayerResponse.class);
	}
	

	public PlayerResponse playerQuit(Integer id) {
		return  root_api_wt.path(String.format("players/%s/quit/", id)).request().get(PlayerResponse.class);
	}

	
}
