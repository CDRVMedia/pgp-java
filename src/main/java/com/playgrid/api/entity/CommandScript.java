package com.playgrid.api.entity;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import com.playgrid.api.client.RestAPI;

@XmlRootElement
public class CommandScript {

	public ArrayList<String> commands;
	public URI success_url = null;
	public URI error_url = null;

	public CommandScript() {
	}

	public void complete(String log, Boolean success) {
		URI url = success ? success_url : error_url;
		CommandScriptLog csl = new CommandScriptLog();
		csl.script_log = log;
		WebTarget webTarget = RestAPI.getInstance().createTarget(url);
		Response response = webTarget.request().put(Entity.json(csl));
		if (response.getStatus() != 200) {
			String code = Integer.toString(response.getStatus());
			throw new WebApplicationException("Unsuccesful status code: "
					+ code);
		}
		response.close();
	}
}
