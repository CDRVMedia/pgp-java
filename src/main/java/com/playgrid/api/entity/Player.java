package com.playgrid.api.entity;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Player extends PlayerResource {

	public ArrayList<String> permission_groups;
	public String status;
	public String username;
	public String reason;
	public Date auth_timestamp;
	public Boolean online;
	public Integer unverified_days;


	public Player() {}

}
