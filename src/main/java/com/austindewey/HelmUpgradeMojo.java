package com.austindewey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "upgrade", defaultPhase = LifecyclePhase.INSTALL)
public class HelmUpgradeMojo extends AbstractMojo {
	
	@Parameter(property = "greeting")
	private String greeting;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Runtime rt = Runtime.getRuntime();
		try {
			Process proc = rt.exec("helm version");
			BufferedReader stdin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stderr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			
			String s;
			while ((s = stdin.readLine()) != null) {
				System.out.println(s);
			}
			
			while ((s = stderr.readLine()) != null) {
				System.out.println(s);
			}
			
			System.out.println("By the way, the greeting is " + greeting);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
