package com.playgrid.api.client.manager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import com.playgrid.api.client.RestAPI;
import com.playgrid.api.entity.Player;
import com.playgrid.api.entity.PlayerAuthorization;
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
	
	
	public PlayerResponse join(Player player) {
		WebTarget webTarget = RestAPI.getInstance().createTarget(player.url);
		return  webTarget.path("join/").request().get(PlayerResponse.class);
	}
	

	public PlayerResponse quit(Player player) {
		WebTarget webTarget = RestAPI.getInstance().createTarget(player.url);
		return  webTarget.path("quit/").request().get(PlayerResponse.class);
	}


	public PlayerResponse authorize(String player_token) {
		return authorize(player_token, true);
	}
	
	
	public PlayerResponse authorize(String player_token, boolean authorization_required) {
		WebTarget webTarget = baseTarget.path("authorize/");

		if (authorization_required == false) {
			webTarget = webTarget.queryParam("authorization_required", authorization_required);
		}
		
		PlayerAuthorization auth = new PlayerAuthorization(player_token);

		return webTarget.request().put(Entity.json(auth), PlayerResponse.class);
		
	}

}
