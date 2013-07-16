package com.playgrid.api.client.manager;

import javax.ws.rs.client.WebTarget;

public abstract class AbstractManager {
	
	protected WebTarget baseTarget;
	
	
	
	public AbstractManager(WebTarget target) {
		this.baseTarget = target;
	}

	
	
	public abstract Object all();												// FIXME: (JP) This should be something like Model or Queryset, Object is too generic

}
