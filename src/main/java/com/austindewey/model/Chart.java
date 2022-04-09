package com.austindewey.model;

public class Chart {
	
	private String name;
	private String version;
	private Repository repository;
	
	public Chart(String name, String version, Repository repository) {
		this.name = name;
		this.version = version;
		this.repository = repository;
	}
	
	public Chart() {}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public Repository getRepository() {
		return repository;
	}

}
