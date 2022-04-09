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
		String helmDir = String.format("%s/helm", getTargetDir(project));
		if (createHelmFolder(helmDir)) {
			getLog().info("Created helm folder at: " + helmDir);
		}
		
		/*
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
			
			System.out.println("By the way, the charts is " + charts.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	private String getTargetDir(MavenProject project) {
		return project.getModel().getBuild().getDirectory();
	}
	
	private boolean createHelmFolder(String helmDir) {
		return new File(helmDir).mkdirs();
	}
}
