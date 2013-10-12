package com.playgrid.api.entity.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class GsonMessageBodyProvider<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

	private static final String UTF_8 = "UTF-8";
	
	private final Gson gson;
	
	
	public GsonMessageBodyProvider() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 

		gson = gsonBuilder.create();
	
	}
	
	
	
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	
	}
	
	
	
	@Override
	public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
		
		InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8);
		
		try {
			Type jsonType;
			
			if (type.equals(genericType)) {
				jsonType = type;
			
			} else {
				jsonType = genericType;
			
			}
			
			return gson.fromJson(streamReader, jsonType);
		
		} catch (Exception e) {
			throw new WebApplicationException(e);

		} finally {
			streamReader.close();
		
		}
	}

	
	
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	
	}

	
	
	@Override
	public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;

	}

	
	
	@Override
	public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

		OutputStreamWriter outputWriter = new OutputStreamWriter(entityStream, UTF_8);

		try {
	      Type jsonType;
	    
	      if (type.equals(genericType)) {
	        jsonType = type;
	      
	      } else {
	        jsonType = genericType;
	      
	      }
	      
	      gson.toJson(t, jsonType, outputWriter);
	    
		} catch (Exception e) {
			throw new WebApplicationException(e);
		
		} finally {
	    	outputWriter.close();
		
	    }
	}
}
