package com.austindewey.model;

import org.apache.maven.plugin.MojoExecutionException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Repository {

	private String name;
	private String url;
	
	public void validate() throws MojoExecutionException {
		if (name == null && url == null) {
			throw new MojoExecutionException("either \"repository.name\" or \"repository.url\" must be defined");
		}
	}
	
}
