package com.playgrid.api.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

public class UserAgentFilter implements ClientRequestFilter {
	
	private final String version;

	
	public UserAgentFilter() {
		
		this.version = this.getAPIVersion();
	
	}

	

	private String getAPIVersion() {											// TODO: (JP) Expand this to support plugin version
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
	
		requestContext.getHeaders().add(HttpHeaders.USER_AGENT, this.version);
		
	}

}
