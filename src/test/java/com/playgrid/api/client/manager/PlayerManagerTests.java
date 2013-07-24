package com.playgrid.api.client.manager;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.playgrid.api.client.RestAPI;
import com.playgrid.api.entity.Method;
import com.playgrid.api.entity.Player;
import com.playgrid.api.entity.PlayerResponse;
import com.playgrid.api.entity.Players;



@RunWith(JUnit4.class)
public class PlayerManagerTests {

	private RestAPI api;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	
	@Before
	public void setUp() {
		
		RestAPI.getConfig().setAccessToken("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4");
		RestAPI.getConfig().setURL("http://local.playgrid.com:8000");
		this.api = RestAPI.getInstance();
		
	}

	
	
	@Test
	public void test_all() {
		Players players = api.getPlayerManager().all();
		Assert.assertEquals(3, players.methods.size());
		Method method = players.methods.get(0);
		Assert.assertTrue(method instanceof Method);
		
		Assert.assertTrue(96 == players.resources.count);
		Assert.assertTrue(10 == players.resources.items.size());
		Player player = players.resources.items.get(0);
		Assert.assertTrue(player instanceof Player);
		
	}



	@Test
	public void test_get() {
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().get("BranchNever");             // FIXME: (JP) Hardcoded token 
		validatePlayerResponse(playerResponse, 2);                              // FIXME: (JP) Methods not consistent
		
	}
	
	
	
	@Test
	public void test_get_or_create() {
		// Test Get
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().get_or_create("BranchNever");   // FIXME: (JP) Hardcoded token
		validatePlayerResponse(playerResponse, 2);                              // FIXME: (JP) Methods not consistent
		
//		// Test Create
//		Assert.assertTrue("Not Implemented", false);                            // FIXME: (JP) Anonymous player creation alters database
//		playerResponse = api.getPlayerAPI().playersGet_or_Create("TestPlayer"); // FIXME: (JP) Hardcoded token
//		validatePlayerResponse(playerResponse, 1);                              // FIXME: (JP) Methods not consistent & null
//		
//		Assert.assertTrue(playerResponse.resources.name.equals("TestPlayer"));
//		Assert.assertTrue(playerResponse.resources.username.equals("anonymous"));
	}
	
	
	
//	@Test
//	public void test_getPlayer() {
//		PlayerResponse playerResponse = api.getPlayerAPI().getPlayer(1);        // FIXME: (JP) Hardcoded ID
//		validatePlayerResponse(playerResponse, 2);                              // FIXME: (JP) Methods not consistent
//		
//	}
	
	
	
	@Test
	public void test_join() {
		// Test with offline Game
		api.getGamesManager().disconnect();
		
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().get("BranchNever");             // FIXME: (JP) Hardcoded token 
		Player player = playerResponse.resources;

		playerResponse = api.getPlayerManager().join(player);
		validatePlayerResponse(playerResponse, 0);                              // FIXME: (JP) Methods not consistent
		Assert.assertFalse(playerResponse.resources.online);

		// Test with online Game
		api.getGamesManager().connect();

		playerResponse = api.getPlayerManager().join(player);
		validatePlayerResponse(playerResponse, 0);                              // FIXME: (JP) Methods not consistent
		Assert.assertTrue(playerResponse.resources.online);

		
	}
	
	
	
	@Test
	public void test_quit() {
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().get("BranchNever");             // FIXME: (JP) Hardcoded token 
		Player player = playerResponse.resources;

		
		playerResponse = api.getPlayerManager().quit(player);
		validatePlayerResponse(playerResponse, 0);                              // FIXME: (JP) Methods not consistent

		Assert.assertFalse(playerResponse.resources.online);

	}
	

	
	private void validatePlayerResponse(PlayerResponse playerResponse, Integer method_count) {
		Assert.assertEquals(method_count, (Integer)playerResponse.methods.size());
		if (method_count > 0) {
			Method method = playerResponse.methods.get(0);
			Assert.assertTrue(method instanceof Method);
		}
		
		Assert.assertTrue(playerResponse.resources instanceof Player);
		Player player = playerResponse.resources;
		Assert.assertTrue(player.name.equals("BranchNever"));
	}

}
