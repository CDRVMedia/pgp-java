package com.playgrid.api.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.playgrid.api.response.GameListResponse;

/**
 * Unit tests for RestAPI
 */
public class RestAPITest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RestAPITest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( RestAPITest.class );
    }

    /**
     * Test
     */
    public void testRestAPI() {
        RestAPI pgpclient = new RestAPI("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4", "http://local.playgrid.com:8000");  // TODO: (JP) Move to a setup method
        GameListResponse response = pgpclient.getGames();
        System.out.println(response);
//        System.out.println(response.getStatus());
    }
}
