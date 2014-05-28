package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerAuthorization {

	@XmlElement
	public String name;
	@XmlElement
	public String uid; // uid provided by bukkit (uuid with dashes stripped)

	public PlayerAuthorization() {
	}

	public PlayerAuthorization(String name, String uid) {
		this.name = name;
		this.uid = uid;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
