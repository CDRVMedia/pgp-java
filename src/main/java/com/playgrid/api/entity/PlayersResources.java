package com.playgrid.api.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class PlayersResources extends BaseResources {

	public ArrayList<PlayerResource> items;

	
	public PlayersResources() {}

}
