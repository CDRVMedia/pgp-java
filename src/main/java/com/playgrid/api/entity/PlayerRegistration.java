package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerRegistration {

	@XmlElement
	public String name;
	@XmlElement
	public String uid; // uid provided by bukkit (uuid with dashes stripped)
	@XmlElement
	public String email;
	@XmlElement
	public String message;

	public PlayerRegistration() {
	}

	public PlayerRegistration(String name, String uid, String email) {
		this.name = name;
		this.email = email;
		this.uid = uid;
	}

	@Override
	public String toString() {
		return this.name + " - " + this.email;
	}
}
