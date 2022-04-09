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
import com.austindewey.model.Values;

@Mojo(name = "upgrade", defaultPhase = LifecyclePhase.INSTALL)
public class HelmUpgradeMojo extends AbstractMojo {
	
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;
	
	@Parameter(property = "releaseName", defaultValue = "${project.name}")
	private String releaseName;
	
	@Parameter(property = "chart", required = true)
	private Chart chart;
	
	@Parameter(property = "values")
	private Values values;
	
	@Parameter(property = "wait")
	private boolean wait;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Runtime rt = Runtime.getRuntime();
		
		String valuesArgs = "";
		String setArgs = "";
		String waitArg = "";
		if (values != null) {
			valuesArgs = values.getValuesArgs();
			setArgs = values.getSetArgs();
		}
		if (wait) {
			waitArg = "--wait";
		}
		
		String helmUpgrade = String.format("helm upgrade --install --repo %s %s %s --version %s %s %s %s", 
				chart.getRepository().getUrl(), releaseName, chart.getName(), chart.getVersion(), valuesArgs, setArgs, waitArg);
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
