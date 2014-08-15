package com.playgrid.api.entity;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.client.WebTarget;
import javax.xml.bind.annotation.XmlRootElement;

import com.playgrid.api.client.RestAPI;

@XmlRootElement
public class Player {

	public String name;
	public URI url;
	public String user_id;
	public String uid; // uid provided by bukkit (uuid with dashes stripped)
	public boolean authorized;
	public Date auth_timestamp;
	public boolean online;
	public String registration;
	public String membership;
	public String[] entitlements;
	public String message;
	public String motd;
	public ArrayList<CommandScript> scripts;
	public ArrayList<OrderLine> pending_order_lines;

	public Player() {
	}

	public WebTarget getTarget() {
		return RestAPI.getInstance().createTarget(url);
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Return list of this player's CommandScripts for execution *note: nulls
	 * the player's reference to the list
	 */
	public ArrayList<CommandScript> getScripts() {
		ArrayList<CommandScript> scripts = this.scripts;
		this.scripts = null;
		return scripts;
	}

	/**
	 * Return list of this player's PendingOrderLines for execution *note: nulls
	 * the player's reference to the list
	 */
	public ArrayList<OrderLine> getLines() {
		ArrayList<OrderLine> lines = this.pending_order_lines;
		this.pending_order_lines = null;
		return lines;
	}
}
