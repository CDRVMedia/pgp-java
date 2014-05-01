package com.playgrid.api.client.manager;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.playgrid.api.client.RestAPI;
import com.playgrid.api.entity.Game;
import com.playgrid.api.entity.Player;
import com.playgrid.api.entity.PlayerRegistration;
import com.playgrid.api.entity.Players;



@RunWith(JUnit4.class)
public class PlayerManagerTests {

	private RestAPI api;
	private Game game;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	
	@Before
	public void setUp() {
		
		RestAPI.getConfig().setAccessToken("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4");
		RestAPI.getConfig().setURL("http://api.local.playgrid.com:8001");
		RestAPI.getConfig().setDebug(true);
		this.api = RestAPI.getInstance();
		this.game = api.getGameManager().self();		
	}

	
	
	@Test
	public void test_all() {
		Players players = api.getPlayerManager().all();
		
		Assert.assertTrue(0 < players.items.size());
		Player player = players.items.get(0);
		Assert.assertTrue(player instanceof Player);
		
	}
	
	
	
	@Test
	public void test_reload() {
		Players players = api.getPlayerManager().all();
		Player player = players.items.get(0);
		Player reloaded = api.getPlayerManager().reload(player);
		
		Assert.assertEquals(player.url, reloaded.url);                          // Make sure urls (id) match
		
	}



	@Test
	public void test_authorize() {
		// Test authorization_required (default)
		String token = "BranchNever";                                           // FIXME: (JP) Hardcoded token
		Player player;
		player = api.getPlayerManager().authorize(token, "");
		validatePlayer(player, token);                       // FIXME: (JP) Methods not consistent

		// Test authorization_required (False) with known player
		player = api.getPlayerManager().authorize(token, "", false);
		validatePlayer(player, token);                       // FIXME: (JP) Methods not consistent

/*
 * This test alters the database
 *  
 *		// Test authorization_required (False) with unknown player
 * 		token = "UnknownPlayer";                                                // FIXME: (JP) Hardcoded token
 * 		playerResponse = api.getPlayerManager().authorize(token, false);
 * 		validatePlayerResponse(playerResponse, token, 0);                       // FIXME: (JP) Methods not consistent
 *
 */
		
	}
	

	
	@Test
	public void test_register() {
		String token = "test";                                                  // FIXME: (JP) Tear down the created player/account
		String email = "test@playgrid.com";

		PlayerRegistration pr;
		pr = api.getPlayerManager().register(token, "", email);
		
		Assert.assertEquals(token, pr.name);
		Assert.assertEquals(email, pr.email);
		Assert.assertEquals("SUCCESS", pr.message);

		token = "BranchNever";                                                  // FIXME: (JP) Hardcoded ID & email
		email = "jason@94920.org";
		pr = api.getPlayerManager().register(token, "", email);
		
		Assert.assertTrue(pr.name.equals(token));
		Assert.assertTrue(pr.email.equals(email));
		Assert.assertTrue(pr.message.equals("ALREADY REGISTERED"));

	}
	
	
	
	@Test
	public void test_join() {
		// Test with offline Game
		api.getGameManager().disconnect(this.game);
		
		String token = "BranchNever";                                           // FIXME: (JP) Hardcoded token
		Player player = api.getPlayerManager().authorize(token, "");

		player = api.getPlayerManager().join(player);
		validatePlayer(player, token);                       // FIXME: (JP) Methods not consistent
		Assert.assertFalse(player.online);

		// Test with online Game
		api.getGameManager().connect(this.game);

		player = api.getPlayerManager().join(player);
		validatePlayer(player, token);                       // FIXME: (JP) Methods not consistent
		Assert.assertTrue(player.online);
		
	}

	
	
	@Test
	public void test_join_with_stats() {
		Assert.assertTrue("Not Implemented", false);
	}
	
	
	
	@Test
	public void test_quit() {
		String token = "BranchNever";                                           // FIXME: (JP) Hardcoded token
		Player player = api.getPlayerManager().authorize(token, "");

		
		player = api.getPlayerManager().quit(player);
		validatePlayer(player, token);                       // FIXME: (JP) Methods not consistent

		Assert.assertFalse(player.online);

	}
	

	
	@Test
	public void test_quit_with_stats() {
		Assert.assertTrue("Not Implemented", false);
	}
	
	
	
	private void validatePlayer(Player player, String token) {		
		Assert.assertTrue(player.name.equals(token));
	}

}
