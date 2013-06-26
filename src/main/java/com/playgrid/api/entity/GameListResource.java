package com.playgrid.api.entity;

import java.net.URI;
import java.util.ArrayList;

public class GameListResource extends BaseListResource {

	public ArrayList<Game> items;

	
	public GameListResource() {}

	public GameListResource(Integer count, URI next, URI previous, ArrayList<Game> items) {
		super(count, next, previous);
		this.items = (items != null) ? new ArrayList<Game>(items) : null;
	}

}
