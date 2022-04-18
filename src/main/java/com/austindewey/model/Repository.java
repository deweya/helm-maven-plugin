package com.austindewey.model;

import org.apache.maven.plugin.MojoExecutionException;

public class Repository {

	private String name;
	private String url;
	
	public Repository() {}
	
	public Repository(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public void validate() throws MojoExecutionException {
		if (name == null && url == null) {
			throw new MojoExecutionException("either \"repository.name\" or \"repository.url\" must be defined");
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
