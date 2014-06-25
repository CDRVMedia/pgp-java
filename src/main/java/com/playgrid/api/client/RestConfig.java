package com.playgrid.api.client;

import javax.naming.ConfigurationException;

public class RestConfig {

	private String accessToken = null;
	private String pgpURL = "http://api.playgrid.com"; // TODO: (JP) Turn into URI
	private String version = "2.1";
	private String locale = "en-US";

	private StringBuilder userAgent = new StringBuilder();

	private boolean debug = false;

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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
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
		return String.format("%s/api/%s/", pgpURL, version);
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Get UserAgent
	 * 
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent.toString();
	}

	/**
	 * Append UserAgent
	 * 
	 * @param userAgent
	 *            the userAgent to append
	 */
	public void appendUserAgent(String userAgent) {
		if (userAgent != null && !userAgent.isEmpty()) {
			this.userAgent.append(" " + userAgent);

		}
	}
}
