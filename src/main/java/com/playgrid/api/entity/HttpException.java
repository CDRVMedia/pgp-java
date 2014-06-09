package com.playgrid.api.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class HttpException {

	@XmlElement
	public String detail;
	
	public HttpException() {}
}
