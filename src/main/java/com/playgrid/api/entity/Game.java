package com.playgrid.api.entity;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Game extends GameResource {

	public Integer heartbeat_interval;
	public Date auth_timestamp;
	public URL website;

	public Game() {}


}
