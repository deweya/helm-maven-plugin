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
		if (url == null) {
			throw new MojoExecutionException("\"repository.url\" must not be null");
		}
	}
	
}
