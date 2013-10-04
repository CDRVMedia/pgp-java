package com.playgrid.api.entity;
import java.net.URI;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusScript {
	public ArrayList<String> commands;	
	public URI success_url = null;
	public URI error_url = null;
	
	public StatusScript() {}
}





