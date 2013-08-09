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
		RestAPI.getConfig().setURL("http://api.local.playgrid.com:8001");
		RestAPI.getConfig().setDebug(true);
		this.api = RestAPI.getInstance();
		
	}

	
	
	@Test
	public void test_all() {
		Players players = api.getPlayerManager().all();
		Assert.assertEquals(3, players.methods.size());
		Method method = players.methods.get(0);
		Assert.assertTrue(method instanceof Method);
		
		Assert.assertEquals(10, players.resources.items.size());
		Player player = players.resources.items.get(0);
		Assert.assertTrue(player instanceof Player);
		
	}



	@Test
	public void test_get() {
		String token = "BranchNever";                                           // FIXME: (JP) Hardcoded token
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().get(token); 
		validatePlayerResponse(playerResponse, token, 2);                       // FIXME: (JP) Methods not consistent
		
	}
	
	
	
	@Test
	public void test_get_or_create() {
		// Test Get
		String token = "BranchNever";                                           // FIXME: (JP) Hardcoded token
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().get_or_create(token);
		validatePlayerResponse(playerResponse, token, 2);                       // FIXME: (JP) Methods not consistent
		
//		// Test Create
//		Assert.assertTrue("Not Implemented", false);                            // FIXME: (JP) Anonymous player creation alters database
//		playerResponse = api.getPlayerAPI().playersGet_or_Create("TestPlayer"); // FIXME: (JP) Hardcoded token
//		validatePlayerResponse(playerResponse, 1);                              // FIXME: (JP) Methods not consistent & null
//		
//		Assert.assertTrue(playerResponse.resources.name.equals("TestPlayer"));
//		Assert.assertTrue(playerResponse.resources.username.equals("anonymous"));
	}
	
	
	
	@Test
	public void test_authorize() {
		// Test authorization_required (default)
		String token = "BranchNever";                                           // FIXME: (JP) Hardcoded token
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().authorize(token);
		validatePlayerResponse(playerResponse, token, 0);                       // FIXME: (JP) Methods not consistent

		// Test authorization_required (False) with known player
		playerResponse = api.getPlayerManager().authorize(token, false);
		validatePlayerResponse(playerResponse, token, 0);                       // FIXME: (JP) Methods not consistent
		

/*
 * This test alter's the database
 *  
 *		// Test authorization_required (False) with unknown player
 * 		token = "UnknownPlayer";                                                // FIXME: (JP) Hardcoded token
 * 		playerResponse = api.getPlayerManager().authorize(token, false);
 * 		validatePlayerResponse(playerResponse, token, 0);                       // FIXME: (JP) Methods not consistent
 *
 */
		
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
		
		String token = "BranchNever";                                           // FIXME: (JP) Hardcoded token
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().get(token);
		Player player = playerResponse.resources;

		playerResponse = api.getPlayerManager().join(player);
		validatePlayerResponse(playerResponse, token, 0);                       // FIXME: (JP) Methods not consistent
		Assert.assertFalse(playerResponse.resources.online);

		// Test with online Game
		api.getGamesManager().connect();

		playerResponse = api.getPlayerManager().join(player);
		validatePlayerResponse(playerResponse, token, 0);                       // FIXME: (JP) Methods not consistent
		Assert.assertTrue(playerResponse.resources.online);

		
	}
	
	
	
	@Test
	public void test_quit() {
		String token = "BranchNever";                                           // FIXME: (JP) Hardcoded token
		PlayerResponse playerResponse;
		playerResponse = api.getPlayerManager().get(token); 
		Player player = playerResponse.resources;

		
		playerResponse = api.getPlayerManager().quit(player);
		validatePlayerResponse(playerResponse, token, 0);                       // FIXME: (JP) Methods not consistent

		Assert.assertFalse(playerResponse.resources.online);

	}
	

	
	private void validatePlayerResponse(PlayerResponse playerResponse, String token, int method_count) {
		Assert.assertEquals(method_count, playerResponse.methods.size());
		if (method_count > 0) {
			Method method = playerResponse.methods.get(0);
			Assert.assertTrue(method instanceof Method);
		}
		
		Assert.assertTrue(playerResponse.resources instanceof Player);
		Player player = playerResponse.resources;
		Assert.assertTrue(player.name.equals(token));
	}

}
