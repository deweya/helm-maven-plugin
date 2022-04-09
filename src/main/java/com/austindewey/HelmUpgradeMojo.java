package com.austindewey;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.austindewey.model.Chart;

@Mojo(name = "upgrade", defaultPhase = LifecyclePhase.INSTALL)
public class HelmUpgradeMojo extends AbstractMojo {
	
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;
	
	@Parameter(property = "charts", required = true)
	private List<Chart> charts;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Runtime rt = Runtime.getRuntime();
		
		String helmDir = String.format("%s/helm", getTargetDir(project));
		if (createHelmFolder(helmDir)) {
			getLog().info("Created helm folder at: " + helmDir);
		}
		
		for (Chart chart : charts) {
			// Pull chart
			getLog().info(String.format("Pulling chart %s, version %s from repository %s", chart.getName(), chart.getVersion(), chart.getRepository().getUrl()));
			String[] args = {"helm", "pull", chart.getName(), "--version", chart.getVersion(), "--repo", chart.getRepository().getUrl(), "--destination", helmDir};
			try {
				Process proc = rt.exec(args);
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
			
			// Install chart
		}
	}
	
	private String getTargetDir(MavenProject project) {
		return project.getModel().getBuild().getDirectory();
	}
	
	private boolean createHelmFolder(String helmDir) {
		return new File(helmDir).mkdirs();
	}
}
