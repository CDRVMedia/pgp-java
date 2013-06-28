package com.playgrid.api.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class APIRoot extends Base {

	public ArrayList<String> resources = new ArrayList<String>();
	
	
	public APIRoot() {}

}
