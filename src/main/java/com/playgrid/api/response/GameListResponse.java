package com.playgrid.api.response;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameListResponse extends BaseResponse {

	public GameListResource resources;
	
	
	public GameListResponse() {}

	public GameListResponse(String name, URI url, String methods, GameListResource resources) {
		super(name, url, methods);
		this.resources = resources;
	}

}
