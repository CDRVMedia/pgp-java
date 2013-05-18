package com.playgrid.api.client;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;




public class RestAPI {
	
	private String AUTH_TOKEN;	
	private String PGP_URL = "http://local.playgrid.com:8000";
	private String PGP_VERSION = "v1";
	private String BASE_URI;
	
	private Client client;
	private WebResource base_wr;

	public RestAPI(String auth_token, String version, String url) {
		
		AUTH_TOKEN = auth_token;
        PGP_VERSION = version;
        BASE_URI = String.format("%s/api/%s", PGP_URL, PGP_VERSION);

        client = Client.create();
        base_wr = client.resource(BASE_URI);
        		
	}
	
	
	
	public Builder buildWebResource(String path) {
		Builder b;
		// Create a new WebResource that accepts JSON media type 
		b = base_wr.path(path).accept(MediaType.APPLICATION_JSON_TYPE);
		// Add authorization header
		b = b.header("AUTHORIZATION", String.format("pgp %s", AUTH_TOKEN));
		
		return b;
	
	}
	
	
	
	public String getGames() {
		return  buildWebResource("games/").get(String.class);
	}
	
}
