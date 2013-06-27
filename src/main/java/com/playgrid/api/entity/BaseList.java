package com.playgrid.api.entity;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class BaseList {

	public Integer count = 0;
	public URI next = null;
	public URI previous = null;
	
}
