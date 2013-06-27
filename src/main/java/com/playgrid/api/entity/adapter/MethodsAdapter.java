package com.playgrid.api.entity.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public final class MethodsAdapter extends XmlAdapter<Methods, Map<String, String>> {

	@Override
	public Methods marshal(Map<String, String> arg0) throws Exception {

		Methods methods = new Methods();
		
		for (Entry<String, String> entry : arg0.entrySet()) {
			MethodsEntry methodsEntry = new MethodsEntry(entry.getKey(), entry.getValue());
			methods.entry.add(methodsEntry);
		}

		return methods;
	}

	@Override
	public Map<String, String> unmarshal(Methods arg0) throws Exception {

		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		for (MethodsEntry methodsEntry : arg0.entry) {
			hashMap.put(methodsEntry.key, methodsEntry.value);
		}
		
		return hashMap;
	}

}
