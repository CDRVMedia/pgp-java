package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class PlayerAuthorization {

	@XmlElement
	public String name;

	
	
	public PlayerAuthorization() {}
	
	
	public PlayerAuthorization(String name) {
	
		this.name = name;
	
	}




	@Override
	public String toString() {

		return this.name;
	
	}
}
