package com.playgrid.api.entity;

import java.net.URI;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Items<T> {

	public int count = 0; // TODO: (JP) Add results per page - rpp
	public URI next = null;
	public URI previous = null;
	public ArrayList<?> items;

	public Items() {
	}
}
