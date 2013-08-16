package com.playgrid.api.client.manager;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.playgrid.api.client.RestAPI;
import com.playgrid.api.entity.GameResponse;
import com.playgrid.api.entity.Games;

public class GameManager extends AbstractManager {
		

	
	public GameManager(WebTarget target) {
		super(target);
	}


	@Override
	public Games all() {
		Response response = baseTarget.request().get();
		return RestAPI.getInstance().translateResponse(response, Games.class);
	}
	
	
//	public GameResponse getGame(Integer id) {									// FIXME: (JP) Should this be get, refresh or reload?
//		Response response = root_api_wt.path(String.format("%s/", id)).request().get();
//		return RestAPI.getInstance().translateResponse(response, GameResponse.class);
//	}

	
	public GameResponse connect() {
		Response response = baseTarget.path("connect/").request().get();
		return RestAPI.getInstance().translateResponse(response, GameResponse.class);
	}

	
	public GameResponse disconnect() {
		Response response = baseTarget.path("disconnect/").request().get();
		return RestAPI.getInstance().translateResponse(response, GameResponse.class);
	}
	
	
	public GameResponse heartbeat() {
		Response response = baseTarget.path("heartbeat/").request().get();
		return RestAPI.getInstance().translateResponse(response, GameResponse.class);
	}
	
}
