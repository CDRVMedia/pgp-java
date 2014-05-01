package com.playgrid.api.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class APIRoot extends Base {

	public List<Endpoint> endpoints;
	public String version;
	
	public APIRoot() {}

}
