package com.austindewey.model;

public class Repository {

	private String name;
	private String url;
	
	public Repository(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public Repository() {}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
}
