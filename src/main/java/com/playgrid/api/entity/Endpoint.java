package com.playgrid.api.entity;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Endpoint {

	public String name;
	public URI url;

	public Endpoint() {
	}

	@Override
	public String toString() {
		return this.name;
	}
}
