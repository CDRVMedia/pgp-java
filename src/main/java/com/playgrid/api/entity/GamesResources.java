package com.playgrid.api.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class GamesResources extends BaseResources {

	public ArrayList<Game> items;

	
	public GamesResources() {}

}
