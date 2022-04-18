package com.austindewey.model;

import org.apache.maven.plugin.MojoExecutionException;

import com.austindewey.helm.CommandType;

public class Chart {
	
	private String name;
	private String version;
	private Repository repository;
	
	public Chart() {}
	
	public Chart(String name, String version, Repository repository) {
		this.name = name;
		this.version = version;
		this.repository = repository;
	}
	
	public CommandType getUpgradeType() {
		if (repository.getUrl() != null && name != null) {
			String r = repository.getUrl().toLowerCase();
			if (r.contains("https://") || r.contains("http://")) {
				return CommandType.UPGRADE_FROM_HTTP_REPOSITORY;
			}
			if (r.contains("oci://")) {
				return CommandType.UPGRADE_FROM_OCI_REGISTRY;
			}
		}
		
		if (repository.getName() != null && name != null) {
			return CommandType.UPGRADE_FROM_ADDED_REPOSITORY;
		}
		
		return CommandType.UPGRADE_FROM_LOCAL;
	}
	
	public void validate() throws MojoExecutionException {
		if (name == null) {
			if (repository.getUrl().toLowerCase().contains("https://") ||
			    repository.getUrl().toLowerCase().contains("http://")  ||
			    repository.getUrl().toLowerCase().contains("oci://")) {

				throw new MojoExecutionException("\"chart.name\" must not be null");
			}
		}
		if (repository == null) {
			throw new MojoExecutionException("\"chart.repository\" must not be null");
		}
		repository.validate();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Repository getRepository() {
		return repository;
	}
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
