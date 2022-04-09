package com.austindewey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "uninstall")
public class HelmUninstallMojo extends AbstractMojo {

	@Parameter(property = "releaseName", defaultValue = "${project.name}")
	private String releaseName;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Runtime rt = Runtime.getRuntime();
		
		String helmUninstall = String.format("helm uninstall %s", releaseName);
		try {
			Process proc = rt.exec(helmUninstall);
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
			e.printStackTrace();
		}
	}
}
