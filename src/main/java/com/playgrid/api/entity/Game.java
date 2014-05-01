package com.playgrid.api.entity;

import java.net.URI;
import java.net.URL;
import java.util.Date;

import javax.ws.rs.client.WebTarget;
import javax.xml.bind.annotation.XmlRootElement;

import com.playgrid.api.client.RestAPI;



@XmlRootElement
public class Game {

	public String name;
	public URI url;
	public boolean online;
	public int heartbeat_interval;
	public Date auth_timestamp;
	public URL website;
	public String[] permission_groups;
	
	public Game() {
	}
	
	public WebTarget getTarget() {
		return RestAPI.getInstance().createTarget(url);
	}


	
	@Override
	public String toString() {
	
		return this.name;
	
	}

}
