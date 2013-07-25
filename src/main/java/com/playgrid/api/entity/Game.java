package com.playgrid.api.entity;

import java.net.URI;
import java.net.URL;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Game {

	public String name;
	public URI url;
	public boolean online;
	public int heartbeat_interval;
	public Date auth_timestamp;
	public URL website;

	public Game() {}


	
	@Override
	public String toString() {
	
		return this.name;
	
	}

}
