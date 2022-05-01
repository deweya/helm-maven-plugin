package com.austindewey.helm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * The base command used to execute inheriting Helm commands
 * 
 * @author Austin Dewey
 */
public abstract class BaseCommand {

	final String releaseName;
	final String namespace;
	final boolean wait;
	
	public BaseCommand(String releaseName, String namespace, boolean wait) {
		this.releaseName = releaseName;
		this.namespace = namespace;
		this.wait = wait;
	}
	
	/**
	 * Generates the Helm command string for the implementing class
	 * 
	 * @return The Helm command string
	 */
	abstract String createCommand();
	
	/**
	 * Adds a common set of flags applicable to all Helm commands
	 * 
	 * @return The common set of Helm flags
	 */
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
	
	/**
	 * Execute the Helm command. Displays all output to the command line and returns a MojoExecutionException for Helm failures.
	 * 
	 * @throws MojoExecutionException
	 */
	public void execute() throws MojoExecutionException {
		try {
			Process proc = Runtime.getRuntime().exec(createCommand());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stderr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			
			String s;
			while ((s = stdin.readLine()) != null) {
				System.out.println(s);
			}
			
			proc.waitFor();
			if (proc.exitValue() != 0) {
				String errMsg = "";
				while ((s = stderr.readLine()) != null) {
					errMsg += s;
				}
				throw new MojoExecutionException(errMsg);
			}
		} catch (Exception e) {
			throw new MojoExecutionException(e);
		} 
	}
}
