package com.playgrid.api.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class GameListResource extends BaseList {

	public ArrayList<Game> items;

	
	public GameListResource() {}

}
