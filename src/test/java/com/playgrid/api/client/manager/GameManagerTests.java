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
import com.playgrid.api.entity.Games;



@RunWith(JUnit4.class)
public class GameManagerTests {

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
        Games games = api.getGameManager().all();
        
        Assert.assertTrue(1 == games.count);
        Game game = games.items.get(0);
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
		api.getGameManager().connect(this.game);
	}
	
	
	
	@Test
	public void test_disconnect() {
		Game game = api.getGameManager().disconnect(this.game);
		validateGame(game);

		Assert.assertFalse(game.online);

	}
	
	
	
	@Test
	public void test_heartbeat() {
		Game game = api.getGameManager().heartbeat(this.game);
		validateGame(game);
		
		Assert.assertTrue(game.online);
	
	}
	
	
	
	private void validateGame(Game game) {        
        Assert.assertTrue(game instanceof Game);
	}

}
