package com.playgrid.api.entity;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class GameResource {

	public String name;
	public URI url; 
	public Boolean online;
	
	
	public GameResource() {}
	
}
