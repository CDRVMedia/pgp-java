package com.playgrid.api.client;


import java.util.ArrayList;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.playgrid.api.entity.APIRoot;
import com.playgrid.api.entity.Game;
import com.playgrid.api.entity.GameResource;
import com.playgrid.api.entity.GameResponse;
import com.playgrid.api.entity.Games;
import com.playgrid.api.entity.Method;
import com.playgrid.api.entity.Player;
import com.playgrid.api.entity.PlayerResource;
import com.playgrid.api.entity.PlayerResponse;
import com.playgrid.api.entity.Players;



@RunWith(JUnit4.class)
public class RestAPITest {

	RestAPI api;
	

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	
	@Before
	public void setUp() {
		api = new RestAPI("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4", 
						  "http://local.playgrid.com:8000"
						  );
	}

	
	
	@Test
	public void test_API_bad_token() {
		api = new RestAPI("bad_token", "http://local.playgrid.com:8000");
		
		exception.expect(NotAuthorizedException.class);
		api.getAPIRoot();

	}

	
	
	@Test
	public void test_API_bad_host() {
		api = new RestAPI("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4", 
						  "http://bad.playgrid.com:8000");

		exception.expect(ProcessingException.class);
		api.getAPIRoot();
	
	}


	
	@Test
	public void test_APIRoot() {
		APIRoot root = api.getAPIRoot();
		Assert.assertEquals(3, root.methods.size());
		
		ArrayList<String> expected_names = new ArrayList<String>();
		expected_names.add("players");
		expected_names.add("games");
		expected_names.add("currencies");

		ArrayList<String> actual_names = new ArrayList<String>();
		for (Method method : root.methods) {
			actual_names.add(method.name);
		}
		
		Assert.assertEquals(expected_names, actual_names);
		
		Assert.assertEquals(0, root.resources.size());
		
	}
	
	
	
	@Test
    public void test_getGames() {
        Games games = api.getGames();
        Assert.assertEquals(1, games.methods.size());
        Method method = games.methods.get(0);
        Assert.assertTrue(method instanceof Method);
        
        Assert.assertTrue(1 == games.resources.count);
        GameResource gameResource = games.resources.items.get(0);
        Assert.assertTrue(gameResource instanceof GameResource);
        
    }
	
	
	
	@Test
	public void test_gamesAuth() {
		GameResponse gameResponse = api.gamesAuth();
		validateGameResponse(gameResponse, 5);
		
	}



	@Test
	public void test_getGame() {
		GameResponse gameResponse = api.getGame(1);								// FIXME: (JP) Hardcoded ID
		validateGameResponse(gameResponse, 5);
		
	}
	
	
	
	@Test
	public void test_gamePlayers() {
		Players players = api.gamePlayers(1);									// FIXME: (JP) Hardcoded ID
		Assert.assertEquals(0, players.methods.size());
		
		Assert.assertTrue(96 == players.resources.count);
		Assert.assertTrue(10 == players.resources.items.size());
		PlayerResource playerResource = players.resources.items.get(0);
		Assert.assertTrue(playerResource instanceof PlayerResource);
		
	}
	
	
	
	@Test
	public void test_gameBalances() {
		Assert.assertTrue("Not Implemented", false);
	
	}
	
	
	
	@Test
	public void test_gameStart() {
		GameResponse gameResponse = api.gameStart(1);							// FIXME: (JP) Hardcoded ID
		validateGameResponse(gameResponse, 0);									// FIXME: (JP) Methods not consistent

		Assert.assertTrue(gameResponse.resources.online);
		
	}
	
	
	
	@Test
	public void test_gameStop() {
		GameResponse gameResponse = api.gameStop(1);							// FIXME: (JP) Hardcoded ID
		validateGameResponse(gameResponse, 0);									// FIXME: (JP) Methods not consistent

		Assert.assertFalse(gameResponse.resources.online);

	}
	
	
	
	@Test
	public void test_gameHeartbeat() {
		GameResponse gameResponse = api.gameHeartbeat(1);						// FIXME: (JP) Hardcoded ID
		validateGameResponse(gameResponse, 0);									// FIXME: (JP) Methods not consistent
		
		Assert.assertTrue(gameResponse.resources.online);
	
	}
	
	
	
	@Test
	public void test_getPlayers() {
		Players players = api.getPlayers();
		Assert.assertEquals(2, players.methods.size());
		Method method = players.methods.get(0);
		Assert.assertTrue(method instanceof Method);
		
		Assert.assertTrue(96 == players.resources.count);
		Assert.assertTrue(10 == players.resources.items.size());
		PlayerResource playerResource = players.resources.items.get(0);
		Assert.assertTrue(playerResource instanceof PlayerResource);
		
	}



	@Test
	public void test_playersGet() {
		PlayerResponse playerResponse = api.playersGet("BranchNever");			// FIXME: (JP) Hardcoded token 
		validatePlayerResponse(playerResponse, 3);								// FIXME: (JP) Methods not consistent
		
	}
	
	
	
	@Test
	public void test_playersGet_or_Create() {
		PlayerResponse playerResponse = api.playersGet_or_Create("BranchNever");// FIXME: (JP) Hardcoded token
		validatePlayerResponse(playerResponse, 3);								// FIXME: (JP) Methods not consistent
		
// FIXME: (JP) Anonymous player creation alters database
		Assert.assertTrue("Not Implemented", false);
//		playerResponse = api.playersGet_or_Create("TestPlayer"); 				// FIXME: (JP) Hardcoded token
//		validatePlayerResponse(playerResponse, 1);								// FIXME: (JP) Methods not consistent & null
//		
//		Assert.assertTrue(playerResponse.resources.name.equals("TestPlayer"));
//		Assert.assertTrue(playerResponse.resources.username.equals("anonymous"));
	}
	
	
	
	@Test
	public void test_getPlayer() {
		PlayerResponse playerResponse = api.getPlayer(1);						// FIXME: (JP) Hardcoded ID
		validatePlayerResponse(playerResponse, 3);								// FIXME: (JP) Methods not consistent
		
	}
	
	
	
	@Test
	public void test_playerBalances() {
		Assert.assertTrue("Not Implemented", false);
	}
	
	
	
	@Test
	public void test_playerJoin() {
		PlayerResponse playerResponse = api.playerJoin(1);						// FIXME: (JP) Hardcoded ID
		validatePlayerResponse(playerResponse, 0);								// FIXME: (JP) Methods not consistent
		
		Assert.assertTrue(playerResponse.resources.online);

	}
	
	
	
	@Test
	public void test_playerQuit() {
		PlayerResponse playerResponse = api.playerQuit(1);						// FIXME: (JP) Hardcoded ID
		validatePlayerResponse(playerResponse, 0);								// FIXME: (JP) Methods not consistent

		Assert.assertFalse(playerResponse.resources.online);

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
