package com.playgrid.api.response;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class BaseResponse {

	public String name;
	public URI url; 
	public String methods;
	

	public BaseResponse() {}
	
	public BaseResponse(String name, URI url, String methods) {
		this.name = name;
		this.url = url;
		this.methods = methods;
	}
	
}
