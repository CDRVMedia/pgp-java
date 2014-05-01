package com.playgrid.api.client;

import java.net.URI;
import java.util.List;

import javax.naming.ConfigurationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheConnector;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.GZipEncoder;

import com.playgrid.api.client.manager.GameManager;
import com.playgrid.api.client.manager.PlayerManager;
import com.playgrid.api.entity.APIRoot;
import com.playgrid.api.entity.Endpoint;
import com.playgrid.api.entity.provider.GsonMessageBodyProvider;
import com.playgrid.api.filter.AuthorizationFilter;
import com.playgrid.api.filter.MediaTypeFilter;
import com.playgrid.api.filter.UserAgentFilter;



public class RestAPI {
	
	private volatile static RestAPI uniqueInstance;
	private static RestConfig config = new RestConfig();
	private Client client;
	private WebTarget root_api_wt;
	private List<Endpoint> endpoints;
	private PlayerManager playerManager;
	private GameManager gameManager;


	
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
        clientConfig.register(GsonMessageBodyProvider.class);                   // Register Gson entity provider
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
	
	
	
	public <T> T translateResponse(Response response, Class<T> responseType) {

		if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            WebApplicationException webAppException;
	        try {
	            final int statusCode = response.getStatus();
	            final Response.Status status = Response.Status.fromStatusCode(statusCode);

	            if (status == null) {
	                final Response.Status.Family statusFamily = response.getStatusInfo().getFamily();
	                webAppException = createExceptionForFamily(response, statusFamily);
	            } else switch (status) {
	                
	            	case BAD_REQUEST:
	                    webAppException = new BadRequestException(response);
	                    break;
	                
	                case UNAUTHORIZED:
	                    webAppException = new NotAuthorizedException(response);
	                    break;
	                
	                case FORBIDDEN:
	                    webAppException = new ForbiddenException(response);
	                    break;
	                
	                case NOT_FOUND:
	                    webAppException = new NotFoundException(response);
	                    break;
	                
	                case METHOD_NOT_ALLOWED:
	                    webAppException = new NotAllowedException(response);
	                    break;
	                
	                case NOT_ACCEPTABLE:
	                    webAppException = new NotAcceptableException(response);
	                    break;
	                
	                case UNSUPPORTED_MEDIA_TYPE:
	                    webAppException = new NotSupportedException(response);
	                    break;
	                
	                case INTERNAL_SERVER_ERROR:
	                    webAppException = new InternalServerErrorException(response);
	                    break;
	                
	                case SERVICE_UNAVAILABLE:
	                    webAppException = new ServiceUnavailableException(response);
	                    break;

	                default:
	                    final Response.Status.Family statusFamily = response.getStatusInfo().getFamily();
	                    webAppException = createExceptionForFamily(response, statusFamily);
	            }

	        } catch (Throwable t) {
	            throw new ProcessingException(LocalizationMessages.RESPONSE_TO_EXCEPTION_CONVERSION_FAILED(), t.getCause());
	        }
	        
	        if (webAppException != null) {
	        	try {
	        		response.close();
	        	} catch (Exception e) {
	        	}
        		throw webAppException;
	        }

		}

		try {
			return response.readEntity(responseType);
		} catch (WebApplicationException e) {
			response.close();
			throw e;
		}
		
	}

	
	
    private WebApplicationException createExceptionForFamily(Response response, Response.Status.Family statusFamily) {
        WebApplicationException webAppException;
        switch (statusFamily) {
            case REDIRECTION:
                webAppException = new RedirectionException(response);
                break;
            case CLIENT_ERROR:
                webAppException = new ClientErrorException(response);
                break;
            case SERVER_ERROR:
                webAppException = new ServerErrorException(response);
                break;
            default:
                webAppException = new WebApplicationException(response);
        }
        return webAppException;
    }
	
	
	public WebTarget createTarget(String url) {
		return client.target(url);
	}

	
	
	public WebTarget createTarget(URI uri) {
		return client.target(uri);
	}

	
	
	// Root
	protected APIRoot getAPIRoot() {
		Response response = root_api_wt.request().get();
		return translateResponse(response, APIRoot.class);
	}
	

	
	public Endpoint getEndpoint(String name) {
		if (endpoints == null) {
			APIRoot base = this.getAPIRoot();
			this.endpoints = base.endpoints;
		}
		
		for (Endpoint e : endpoints) {
			if (e.name.equals(name)) {
				return e;
			}
		}
	
		String msg = String.format("Endpont '%s' Not Found", name);
		throw new NotAllowedException(msg, endpoints.toString(), (String[])null);
	
	}
	
	
	
	public WebTarget getEndpointTarget(String name) {
		Endpoint ep = this.getEndpoint(name);
		return this.createTarget(ep.url);
	}
	
	
	
	public GameManager getGameManager() { 
		if(gameManager == null)
			gameManager = new GameManager();
		return gameManager;
	}



	public PlayerManager getPlayerManager() {
		if(playerManager == null)
			playerManager = new PlayerManager();
		return playerManager;
	}
	
}
