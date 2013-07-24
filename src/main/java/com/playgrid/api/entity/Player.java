package com.playgrid.api.entity;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Player {

	public String name;
	public URI url;
	public ArrayList<String> permission_groups;
	public Status status;
	public String user_id;
	public String reason;
	public Date suspended_until;
	public Date auth_timestamp;
	public Boolean online;
	public Integer unverified_days;


	public Player() {}


	
	@Override
	public String toString() {
	
		return this.name;
	
	}


	/**
	 * Status to communicate to games to determine player authorization
	 * @author jpage
	 *
	 */
	public static enum Status {

		/*
		 * Player is authorized for game
		 */
		AUTHORIZED, 

		/*
		 * Player is banned from game
		 */
		BANNED,
		
		/*
		 * An error occurred
		 */
		ERROR, 
		
		/*
		 * Player has no status or is anonymous
		 */
		NONE, 
		
		/*
		 * Player is suspended from game
		 */
		SUSPENDED, 
		
		/*
		 * Player has not verified PlayGrid account
		 */
		UNVERIFIED, 
	}


}
