package com.playgrid.api.client.manager;

import javax.ws.rs.client.WebTarget;

import com.playgrid.api.entity.GameResponse;
import com.playgrid.api.entity.Games;

public class GameManager extends AbstractManager {
		

	
	public GameManager(WebTarget target) {
		super(target);
	}


	@Override
	public Games all() {
		return baseTarget.request().get(Games.class);	
	}
	
	
//	public GameResponse getGame(Integer id) {									// FIXME: (JP) Should this be get, refresh or reload?
//		return  root_api_wt.path(String.format("%s/", id)).request().get(GameResponse.class);
//	}

	
	public GameResponse connect() {
		return  baseTarget.path("connect/").request().get(GameResponse.class);
	}

	
	public GameResponse disconnect() {
		return  baseTarget.path("disconnect/").request().get(GameResponse.class);
	}
	
	
	public GameResponse heartbeat() {
		return  baseTarget.path("heartbeat/").request().get(GameResponse.class);
	}

	
}
