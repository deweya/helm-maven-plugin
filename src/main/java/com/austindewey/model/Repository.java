package com.austindewey.model;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Represents a chart repository
 * 
 * @author Austin Dewey
 */
public class Repository {

	private String name;
	private String url;
	private String username = System.getenv("HELM_MAVEN_PLUGIN_USERNAME");
	private String password = System.getenv("HELM_MAVEN_PLUGIN_PASSWORD");
	
	public Repository() {}
	
	public Repository(String name, String url, String username, String password) {
		this.name = name;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Validates the repository object
	 * 
	 * @throws MojoExecutionException An exception is thrown if the repository fails validation
	 */
	public void validate() throws MojoExecutionException {
		if (name == null && url == null) {
			throw new MojoExecutionException("either \"repository.name\" or \"repository.url\" must be defined");
		}
		
		if (username != null && password == null ||
			username == null && password != null) {
			throw new MojoExecutionException("both repository username and password must be provided for basic auth");
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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
