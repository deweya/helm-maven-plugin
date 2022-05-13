package com.austindewey;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.austindewey.helm.UninstallCommand;

/**
 * Uninstall a Helm release. This is the equivalent of passing "helm uninstall" from the Helm CLI
 * 
 * @author Austin Dewey
 */
@Mojo(name = "uninstall")
public class HelmUninstallMojo extends AbstractMojo {

	/**
	 * The Helm release name
	 */
	@Parameter(property = "releaseName", defaultValue = "${project.name}")
	private String releaseName;
	
	/**
	 * The target Kubernetes namespace
	 */
	@Parameter(property = "namespace")
	private String namespace;
	
	/**
	 * Wait for the uninstall operation to complete
	 */
	@Parameter(property = "wait")
	private boolean wait;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info(String.format("Uninstalling release \"%s\"", releaseName));
		
		new UninstallCommand.Builder(releaseName)
				.namespace(namespace)
				.wait(wait)
				.build()
				.execute();
	}
}
