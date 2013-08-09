package com.playgrid.api.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

public class UserAgentFilter implements ClientRequestFilter {
	
	private final StringBuilder versionBuilder = new StringBuilder();

	
	public UserAgentFilter() {
		
		versionBuilder.append(String.format("PGP/%s", this.getAPIVersion()));
	
	}

	

	public UserAgentFilter(String userAgent) {

		this();
		versionBuilder.append(userAgent);
	
	}



	private String getAPIVersion() {

		String path = "/version.properties";
		InputStream stream = getClass().getResourceAsStream(path);
		
		String version = "UNKNOWN";
		if (stream == null) return version;

	    Properties props = new Properties();
        try {
            props.load(stream);
            stream.close();
            return (String)props.get("pgp.api.version");
        
        } catch (IOException e) {
            return version;
        
        }
	
	}

	
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
	
		requestContext.getHeaders().add(HttpHeaders.USER_AGENT, versionBuilder.toString());
		
	}

}
