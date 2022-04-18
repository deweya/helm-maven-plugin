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
	
	@Parameter(property = "namespace")
	private String namespace;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		chart.validate();
		
		Runtime rt = Runtime.getRuntime();
		
		String valuesArgs = "";
		String setArgs = "";
		String waitArg = "";
		String namespaceArg = "";
		String versionArg = "";
		if (values != null) {
			valuesArgs = values.getValuesArgs();
			setArgs = values.getSetArgs();
		}
		if (wait) {
			waitArg = "--wait";
		}
		if (namespace != null) {
			namespaceArg = "--namespace " + namespace;
		}
		if (chart.getVersion() != null) {
			versionArg = "--version " + chart.getVersion();
		}
		
		String helmUpgrade = "";
		String url = chart.getRepository().getUrl();
		if (url != null) {
			url = url.toLowerCase();
		} else {
			url = "";
		}
		// Install from http(s) repository
		if (url.contains("https://") || url.contains("http://")) {
			helmUpgrade = String.format("helm upgrade --install --repo %s %s %s %s %s %s %s %s", 
					url, releaseName, chart.getName(), versionArg, valuesArgs, setArgs, waitArg, namespaceArg);
		// Install from oci registry
		} else if (url.contains("oci://")) {
			helmUpgrade = String.format("helm upgrade --install %s %s/%s %s %s %s %s %s", 
					releaseName, url, chart.getName(), versionArg, valuesArgs, setArgs, waitArg, namespaceArg);
		// Install from local file
		} else if (chart.getRepository().getUrl() != null) {
			helmUpgrade = String.format("helm upgrade --install %s %s %s %s %s %s", 
					releaseName, url, valuesArgs, setArgs, waitArg, namespaceArg);
		// Install from an already-existing repository (added previously from "helm repo add")
		} else if (chart.getRepository().getName() != null) {
			helmUpgrade = String.format("helm upgrade --install %s %s/%s %s %s %s %s",
					releaseName, chart.getRepository().getName(), chart.getName(), valuesArgs, setArgs, waitArg, namespaceArg);
		}

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
