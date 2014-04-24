package com.playgrid.api.client;


import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.playgrid.api.entity.APIRoot;
import com.playgrid.api.entity.Endpoint;



@RunWith(JUnit4.class)
public class RestAPITests {

	RestAPI api;
	

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	
	@Before
	public void setUp() {
		
		RestAPI.getConfig().setAccessToken("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4");
		RestAPI.getConfig().setURL("http://api.local.playgrid.com:8001");
		RestAPI.getConfig().setDebug(true);
		api = RestAPI.getInstance();
		
	}


//	@Test																		// FIXME: (JP) singleton tests fail, test with a protected constructor?
//	public void test_API_bad_token() {
//
//		RestAPI.getConfig().setAccessToken("bad_token");
//		RestAPI.getConfig().setURL("http://local.playgrid.com:8000");
//		api = RestAPI.getInstance();
//
//		exception.expect(NotAuthorizedException.class);
//		api.getAPIRoot();
//
//	}

	
	
//	@Test
//	public void test_API_bad_host() {
//		
//		RestAPI.getConfig().setAccessToken("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4");
//		RestAPI.getConfig().setURL("http://bad.playgrid.com:8000");
//		api = RestAPI.getInstance();
//		
//		exception.expect(ProcessingException.class);							// FIXME: (JP) Really want a 'host not found' exception
//		api.getAPIRoot();
//	
//	}

	
	@Test
	public void test_appendingUserAgent() {
		String userAgent = RestAPI.getConfig().getUserAgent();
		
		String testUA = "TestUA/1000";
		RestAPI.getConfig().appendUserAgent(testUA);
		Assert.assertEquals(userAgent+" "+testUA, RestAPI.getConfig().getUserAgent());

		userAgent = RestAPI.getConfig().getUserAgent();
		
		testUA = "TestUA/2000";
		RestAPI.getConfig().appendUserAgent(testUA);
		Assert.assertEquals(userAgent+" "+testUA, RestAPI.getConfig().getUserAgent());
		
	}

	
	
	@Test
	public void test_APIRoot() {
		APIRoot root = api.getAPIRoot();
		Assert.assertEquals(4, root.methods.size());
		
		ArrayList<String> expected_names = new ArrayList<String>();
		expected_names.add("players");
		expected_names.add("games");
		expected_names.add("users");
		expected_names.add("order-lines");

		ArrayList<String> actual_names = new ArrayList<String>();
		for (Endpoint method : root.methods) {
			actual_names.add(method.name);
		}
		
		Assert.assertEquals(expected_names, actual_names);
		
		Assert.assertEquals(0, root.resources.size());
		
	}

}
