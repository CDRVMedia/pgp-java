package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class PlayerAuthorization {

	@XmlElement
	public String player_token;

	
	
	public PlayerAuthorization() {}
	
	
	public PlayerAuthorization(String player_token) {
	
		this.player_token = player_token;
	
	}




	@Override
	public String toString() {

		return this.player_token;
	
	}
}
