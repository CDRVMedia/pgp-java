package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class PlayerRegistration {

	@XmlElement
	public String player_token;
	@XmlElement
	public String email;
	@XmlElement
	public String message;

	
	
	public PlayerRegistration() {}
	
	
	public PlayerRegistration(String player_token, String email) {
	
		this.player_token = player_token;
		this.email = email;
	
	}




	@Override
	public String toString() {

		return this.player_token + " - " + this.email;
	
	}
}
