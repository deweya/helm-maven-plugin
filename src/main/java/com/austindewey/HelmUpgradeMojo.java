package com.austindewey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

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
		
		for (Chart chart : charts) {
			// Install chart
			StringBuilder values = new StringBuilder();
			if (chart.getValues() != null && chart.getValues().getFiles() != null) {
				for (String file : chart.getValues().getFiles()) {
					values.append(String.format("--values %s ", file));
				}
			}
			
			StringBuilder set = new StringBuilder();
			if (chart.getValues() != null && chart.getValues().getSet() != null) {
				for (Map.Entry<String,String> entry : chart.getValues().getSet().entrySet()) {
					set.append(String.format("--set %s=\"%s\" ", entry.getKey(), entry.getValue()));
				}
			}
			
			String helmUpgrade = String.format("helm upgrade --install --repo %s %s %s --version %s %s %s", 
					chart.getRepository().getUrl(), project.getName(), chart.getName(), chart.getVersion(), values.toString(), set.toString());
			try {
				Process proc = rt.exec(helmUpgrade);
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
}
