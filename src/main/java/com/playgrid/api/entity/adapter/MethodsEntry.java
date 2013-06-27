package com.playgrid.api.entity.adapter;

import javax.xml.bind.annotation.XmlAttribute;

public class MethodsEntry {
	
	@XmlAttribute
	public String key;
	
	@XmlAttribute
	public String value;


	public MethodsEntry(String key, String value) {
		this.key = key;
		this.value = value;
	}
	

}
