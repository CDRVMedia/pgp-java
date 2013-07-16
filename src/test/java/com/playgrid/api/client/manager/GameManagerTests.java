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
import com.playgrid.api.entity.GameResponse;
import com.playgrid.api.entity.Games;
import com.playgrid.api.entity.Method;



@RunWith(JUnit4.class)
public class GameManagerTests {

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
        Games games = api.getGamesManager().all();
        Assert.assertEquals(3, games.methods.size());
        Method method = games.methods.get(0);
        Assert.assertTrue(method instanceof Method);
        
        Assert.assertTrue(1 == games.resources.count);
        Game game = games.resources.items.get(0);
        Assert.assertTrue(game instanceof Game);
        
    }
	
	
	
//	@Test
//	public void test_getGame() {
//		GameResponse gameResponse = api.getGame(1);								// FIXME: (JP) Hardcoded ID
//		validateGameResponse(gameResponse, 0);
//		
//	}
	
	
	
	@Test
	public void test_connect() {
		GameResponse gameResponse = api.getGamesManager().connect();
		validateGameResponse(gameResponse, 0);

		Assert.assertTrue(gameResponse.resources.online);
		
	}
	
	
	
	@Test
	public void test_disconnect() {
		GameResponse gameResponse = api.getGamesManager().disconnect();
		validateGameResponse(gameResponse, 0);

		Assert.assertFalse(gameResponse.resources.online);

	}
	
	
	
	@Test
	public void test_heartbeat() {
		GameResponse gameResponse = api.getGamesManager().heartbeat();
		validateGameResponse(gameResponse, 0);
		
		Assert.assertTrue(gameResponse.resources.online);
	
	}
	
	
	
	private void validateGameResponse(GameResponse gameResponse, Integer method_count) {
		Assert.assertEquals(method_count, (Integer)gameResponse.methods.size());
		if (method_count > 0) {
			Method method = gameResponse.methods.get(0);
	        Assert.assertTrue(method instanceof Method);
		}
        
        Assert.assertTrue(gameResponse.resources instanceof Game);
        Game game = gameResponse.resources;
        Assert.assertTrue(game.name.equals("Jason and Justin's world"));
	}

}
