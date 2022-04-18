package com.austindewey;

import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.austindewey.helm.UpgradeFromAddedRepositoryCommand;
import com.austindewey.helm.UpgradeFromHttpRepositoryCommand;
import com.austindewey.helm.UpgradeFromLocalChartCommand;
import com.austindewey.helm.UpgradeFromOciRegistryCommand;
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
		
		List<String> valuesFiles = null;
		Map<String,String> inlineValues = null;
		
		if (values != null) {
			valuesFiles = values.getFiles();
			inlineValues = values.getSet();
		}
		
		String chartName = chart.getName();
		String repositoryUrl = chart.getRepository().getUrl();
		String repositoryName = chart.getRepository().getName();
		String chartVersion = chart.getVersion();
		
		switch (chart.getUpgradeType()) {
		case UPGRADE_FROM_HTTP_REPOSITORY:
			new UpgradeFromHttpRepositoryCommand.Builder(releaseName, chartName, repositoryUrl)
					.inlineValues(inlineValues)
					.valuesFiles(valuesFiles)
					.version(chartVersion)
					.wait(wait)
					.namespace(namespace)
					.build()
					.execute();
			break;
		case UPGRADE_FROM_OCI_REGISTRY:
			new UpgradeFromOciRegistryCommand.Builder(releaseName, chartName, repositoryUrl)
					.inlineValues(inlineValues)
					.valuesFiles(valuesFiles)
					.version(chartVersion)
					.wait(wait)
					.namespace(namespace)
					.build()
					.execute();
			break;
		case UPGRADE_FROM_ADDED_REPOSITORY:
			new UpgradeFromAddedRepositoryCommand.Builder(releaseName, chartName, repositoryName)
					.inlineValues(inlineValues)
					.valuesFiles(valuesFiles)
					.version(chartVersion)
					.wait(wait)
					.namespace(namespace)
					.build()
					.execute();
			break;
		case UPGRADE_FROM_LOCAL:
			new UpgradeFromLocalChartCommand.Builder(releaseName, repositoryUrl)
					.inlineValues(inlineValues)
					.valuesFiles(valuesFiles)
					.wait(wait)
					.namespace(namespace)
					.build()
					.execute();
			break;
		default:
			break;
		}
		
	}
}
