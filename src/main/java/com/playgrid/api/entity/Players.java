package com.playgrid.api.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Players extends BaseList {

	public ArrayList<Player> items;

	public Players() {
	}

}
