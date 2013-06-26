package com.playgrid.api.entity;

import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class BaseResponse {

	public String name;
	public URI url; 
	public HashMap<String, Link> methods;
	

	public BaseResponse() {}
	
	public BaseResponse(String name, URI url, HashMap<String, Link> methods) {
		this.name = name;
		this.url = url;
		this.methods = methods;
	}
	
}
