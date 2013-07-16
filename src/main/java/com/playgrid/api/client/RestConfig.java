package com.playgrid.api.client;

import javax.naming.ConfigurationException;



public class RestConfig {
	
	private String accessToken = null;
	private String pgpURL = "http://www.playgrid.com";                          // TODO: (JP) Turn into URI
	private String version = "1.1";
	
	public String getAccessToken() throws ConfigurationException {
		if (accessToken == null) {
			throw new ConfigurationException("PGP Access Token Required");
		}
		return accessToken;
	}
	
	public void setAccessToken(String token) {
		this.accessToken = token;
	}
	
	public String getURL() {
		return pgpURL;
	}
	
	public void setURL(String url) {
		if (url != null && !url.isEmpty()) {
			this.pgpURL = url;
		}
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		if (version != null && !version.isEmpty()) {
			this.version = version;
		}
	}
	
	public String getAPI_URI() {
		return String.format("%s/api/%s", pgpURL, version);
	}

}



