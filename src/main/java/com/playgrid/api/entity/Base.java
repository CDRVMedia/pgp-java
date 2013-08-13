package com.playgrid.api.entity;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public abstract class Base {

	public String name;
	public URI url;
	
	public List<Method> methods;

	
	@Override
	public String toString() {

		return this.name;
	
	}
	
}
