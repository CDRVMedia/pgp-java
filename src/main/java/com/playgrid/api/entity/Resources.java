package com.playgrid.api.entity;

import java.net.URI;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Resources<T> {

	public Integer count = 0;
	public URI next = null;
	public URI previous = null;
	public ArrayList<?> items;
	
	
	public Resources() {}
	
}
