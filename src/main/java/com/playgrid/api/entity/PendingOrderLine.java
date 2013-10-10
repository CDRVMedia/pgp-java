package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class PendingOrderLine {
	public CommandScript script;	
	public String message;	
	
	public PendingOrderLine() {}
}





