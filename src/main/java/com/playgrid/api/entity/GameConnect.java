package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class GameConnect {

	@XmlElement
	public String warning_message;
	@XmlElement
	public String message;

	
	public GameConnect() {}
	

}