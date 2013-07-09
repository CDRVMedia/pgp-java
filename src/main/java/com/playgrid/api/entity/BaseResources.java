package com.playgrid.api.entity;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class BaseResources {

	public Integer count = 0;													// TODO: (JP) Add results per page - rpp
	public URI next = null;
	public URI previous = null;
	
}
