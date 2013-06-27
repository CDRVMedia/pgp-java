package com.playgrid.api.entity;

import java.net.URI;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public abstract class Base {

	public String name;
	public URI url;
	
	public ArrayList<Method> methods;
	
}
