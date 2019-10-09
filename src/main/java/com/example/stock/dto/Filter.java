package com.example.stock.dto;

import java.io.Serializable;

public class Filter implements Serializable{

	private static final long serialVersionUID = 2919033260357715283L;
	private String key;
	private String name;
	private String direction;
	private double value;
	
	public Filter() {
		super();
	}
	
	public Filter(String key, String name, String direction) {
		super();
		this.key = key;
		this.name = name;
		this.direction = direction;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
}
