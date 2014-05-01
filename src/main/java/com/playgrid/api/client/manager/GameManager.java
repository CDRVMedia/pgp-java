package com.playgrid.api.client.manager;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.playgrid.api.client.RestAPI;
import com.playgrid.api.entity.Game;
import com.playgrid.api.entity.Games;

public class GameManager extends AbstractManager {
		

	
	public GameManager() {
		super();
	}


	@Override
	public Games all() {
		WebTarget target = restAPI.getEndpointTarget("game:list");
		Response response = target.request().get();
		return restAPI.translateResponse(response, Games.class);
	}
	
	
//	public GameResponse getGame(Integer id) {									// FIXME: (JP) Should this be get, refresh or reload?
//		Response response = root_api_wt.path(String.format("%s/", id)).request().get();
//		return RestAPI.getInstance().translateResponse(response, GameResponse.class);
//	}

	
	public Game connect(Game game) {
		Response response = game.getTarget().path("connect/").request().get();
		return RestAPI.getInstance().translateResponse(response, Game.class);
	}

	
	public Game disconnect(Game game) {
		Response response = game.getTarget().path("disconnect/").request().get();
		return RestAPI.getInstance().translateResponse(response, Game.class);
	}
	
	
	public Game heartbeat(Game game) {
		Response response = game.getTarget().path("heartbeat/").request().get();
		return RestAPI.getInstance().translateResponse(response, Game.class);
	}
	
	
	public Game self() {
		WebTarget target = restAPI.getEndpointTarget("game:self");		
		Response response = target.request().get();
		return RestAPI.getInstance().translateResponse(response, Game.class);
	}
	
}
