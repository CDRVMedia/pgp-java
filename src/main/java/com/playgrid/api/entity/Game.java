package com.playgrid.api.entity;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Game {

	public String name;
	public URI url; 
	public Boolean online;
	
	public Game() {}
	
	
	public Game(String name, URI url, Boolean online) {
		this.name = name;
		this.url = url; 
		this.online = online;
	}

}
