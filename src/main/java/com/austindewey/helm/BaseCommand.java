package com.austindewey.helm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class BaseCommand {

	final String releaseName;
	final String namespace;
	final boolean wait;
	
	public BaseCommand(String releaseName, String namespace, boolean wait) {
		this.releaseName = releaseName;
		this.namespace = namespace;
		this.wait = wait;
	}
	
	abstract String createCommand();
	
	String addCommonFlags() {
		String flags = "";
		
		if (wait) {
			flags += "--wait ";
		}
		if (namespace != null) {
			flags += String.format("--namespace %s ", namespace);
		}
		
		return flags;
	}
	
	public void execute() throws MojoExecutionException {
		try {
			Process proc = Runtime.getRuntime().exec(createCommand());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stderr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			
			String s;
			while ((s = stdin.readLine()) != null) {
				System.out.println(s);
			}
			
			while ((s = stderr.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			throw new MojoExecutionException(e);
		}
	}
}
