package com.austindewey.model;

import org.apache.maven.plugin.MojoExecutionException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Chart {
	
	private String name;
	private String version;
	private Repository repository;
	
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
}
