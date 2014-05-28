package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderLine {
	public Product product;
	public int quantity;
	public String status;
	public CommandScript script;
	public String message;

	public OrderLine() {
	}
}
