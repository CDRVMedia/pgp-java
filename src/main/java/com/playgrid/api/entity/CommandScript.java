package com.playgrid.api.entity;
import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.annotation.XmlRootElement;

import com.playgrid.api.client.RestAPI;

@XmlRootElement
public class CommandScript {
	public ArrayList<String> commands;	
	public URI success_url = null;
	public URI error_url = null;
	
	private void complete(String log, URI url) {
		CommandScriptLog csl = new CommandScriptLog();
		csl.script_log = log;
		WebTarget webTarget = RestAPI.getInstance().createTarget(success_url);
		webTarget.request().put(Entity.json(log));
	}
	
	public CommandScript() {}
	
	public void error(String log) {
		complete(log, error_url);
	}
	
	public void success(String log) {
		complete(log, success_url);
	}
}





