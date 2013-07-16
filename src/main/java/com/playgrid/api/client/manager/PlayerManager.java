package com.playgrid.api.client.manager;

import javax.ws.rs.client.WebTarget;

import com.playgrid.api.entity.PlayerResponse;
import com.playgrid.api.entity.Players;

public class PlayerManager extends AbstractManager {

	
	
	public PlayerManager(WebTarget target) {
		super(target);
	}
	
	
	
	@Override
	public Players all() {
		return baseTarget.request().get(Players.class);	
	}

	
	public PlayerResponse get(String player_token) {
		return  baseTarget.path(String.format("get/%s/", player_token)).request().get(PlayerResponse.class);
	}

	
	public PlayerResponse get_or_create(String player_token) {
		return  baseTarget.path(String.format("get_or_create/%s/", player_token)).request().get(PlayerResponse.class);
	}
	
	
//	public PlayerResponse getPlayer(Integer id) {
//		return  baseTarget.path(String.format("%s/", id)).request().get(PlayerResponse.class);
//	}
	
	
	public PlayerResponse join(Integer id) {
		return  baseTarget.path(String.format("%s/join/", id)).request().get(PlayerResponse.class);
	}
	

	public PlayerResponse quit(Integer id) {
		return  baseTarget.path(String.format("%s/quit/", id)).request().get(PlayerResponse.class);
	}



}
