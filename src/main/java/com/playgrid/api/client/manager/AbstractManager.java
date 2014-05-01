package com.playgrid.api.client.manager;

import com.playgrid.api.client.RestAPI;

public abstract class AbstractManager {
	protected RestAPI restAPI = RestAPI.getInstance();;
	
	
	public AbstractManager() {
	}

	
	
	public abstract Object all();												// FIXME: (JP) This should be something like Model or Queryset, Object is too generic

}
