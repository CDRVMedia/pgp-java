package com.playgrid.api.client;


import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.playgrid.api.entity.APIRoot;
import com.playgrid.api.entity.GameResource;
import com.playgrid.api.entity.Games;
import com.playgrid.api.entity.Method;



@RunWith(JUnit4.class)
public class RestAPITest {

	RestAPI api;
	
	
	@Before
	public void setUp() {
		api = new RestAPI("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4", 
						  "http://local.playgrid.com:8000"
						  );
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
        Assert.assertTrue(method.name.equals("auth"));
        
        Assert.assertTrue(1 == games.resources.count);
        GameResource gameResource = games.resources.items.get(0);
        Assert.assertTrue(gameResource.name.equals("Jason and Justin's world"));
        
    }
}
