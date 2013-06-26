package com.playgrid.api.entity;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class BaseListResource {

	public Integer count;
	public URI next;
	public URI previous;

	
	public BaseListResource() {};
	
	public BaseListResource(Integer count, URI next, URI previous) {
		this.count = count;
		this.next = next;
		this.previous = previous; 
	}
	
}
