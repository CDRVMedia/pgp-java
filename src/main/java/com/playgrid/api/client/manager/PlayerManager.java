package com.playgrid.api.client.manager;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.playgrid.api.client.RestAPI;
import com.playgrid.api.entity.Player;
import com.playgrid.api.entity.PlayerAuthorization;
import com.playgrid.api.entity.PlayerRegistration;
import com.playgrid.api.entity.PlayerRegistrationResponse;
import com.playgrid.api.entity.PlayerResponse;
import com.playgrid.api.entity.Players;

public class PlayerManager extends AbstractManager {
	
	
	public PlayerManager(WebTarget target) {
		super(target);
	}
	
	
	@Override
	public Players all() {
		Response response = baseTarget.request().get();
		return RestAPI.getInstance().translateResponse(response, Players.class);
	}

	
//	public PlayerResponse getPlayer(Integer id) {
//		Response response = baseTarget.path(String.format("%s/", id)).request().get();
//		return RestAPI.getInstance().translateResponse(response, PlayerResponse.class);
//	}
	
	
	public PlayerResponse join(Player player) {
		WebTarget webTarget = RestAPI.getInstance().createTarget(player.url);
		Response response = webTarget.path("join/").request().get();
		return RestAPI.getInstance().translateResponse(response, PlayerResponse.class);
	}
	

	public PlayerResponse quit(Player player) {
		WebTarget webTarget = RestAPI.getInstance().createTarget(player.url);
		Response response = webTarget.path("quit/").request().get();
		return RestAPI.getInstance().translateResponse(response, PlayerResponse.class);
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

		Response response = webTarget.request().put(Entity.json(auth));
		if (response.getStatus() == 405) {
			throw new NotAllowedException(response);
		}
		return RestAPI.getInstance().translateResponse(response, PlayerResponse.class);

	}


	public PlayerRegistrationResponse register(String player_token, String email) {
		WebTarget webTarget = baseTarget.path("register/");

		PlayerRegistration reg = new PlayerRegistration(player_token, email);
		
		Response response = webTarget.request().put(Entity.json(reg));
		return RestAPI.getInstance().translateResponse(response, PlayerRegistrationResponse.class);
		
	}

}
