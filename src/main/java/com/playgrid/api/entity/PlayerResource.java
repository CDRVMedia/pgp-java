package com.playgrid.api.entity;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class PlayerResource {

	public String name;
	public URI url; 
	
	
	public PlayerResource() {}
	

	@Override
	public String toString() {

		return this.name;
	
	}
	
}
