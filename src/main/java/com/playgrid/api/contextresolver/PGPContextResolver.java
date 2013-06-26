//package com.playgrid.api.contextresolver;
//
//import javax.ws.rs.ext.ContextResolver;
//import javax.ws.rs.ext.Provider;
//import javax.xml.bind.JAXBContext;
//
//import com.playgrid.api.response.Game;
//import com.playgrid.api.response.GameListResponse;
//import com.sun.jersey.api.json.JSONConfiguration;
//import com.sun.jersey.api.json.JSONJAXBContext;
//
//
//
//@Provider
//public class PGPContextResolver implements ContextResolver<JAXBContext> {
//
//	private JAXBContext context;
//	private Class<?>[] types = {String.class,
//								GameListResponse.class, 
//								Game.class,
//								};
//	
//	
//	public PGPContextResolver() throws Exception {
//		
//		JSONConfiguration JSONConf = JSONConfiguration.mapped().arrays("methods").build();
//		this.context = new JSONJAXBContext(JSONConf, types);
//	}
//	
//	@Override
//	public JAXBContext getContext(Class<?> objectType) {
//	
//		for (Class<?> type : types) {
//			if (type == objectType) {
//				return context;
//			}
//	 	}
//		return null;
//	}
//
//}
