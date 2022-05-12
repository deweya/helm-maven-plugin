package com.austindewey.helm;

import junit.framework.Assert;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CommandTest {

	@Test
	public void givenUninstallCommand_whenNoCommonFlagsPassed_thenCreateMinimumCommand() {
		UninstallCommand cmd = new UninstallCommand.Builder("test").build();
		String expected = "helm uninstall test ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUninstallCommand_whenAllCommonFlagsPassed_thenCreateFullCommand() {
		UninstallCommand cmd = new UninstallCommand.Builder("test")
									.wait(true)
									.namespace("testns")
									.build();
		String expected = "helm uninstall test --wait --namespace testns ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUpgradeFromAddedRepositoryCommand_whenNoAddedFlagsPassed_thenCreateMinimumCommand() {
		UpgradeFromAddedRepositoryCommand cmd = new UpgradeFromAddedRepositoryCommand.Builder("test", "nginx", "testrepo").build();
		String expected = "helm upgrade --install test testrepo/nginx ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUpgradeFromAddedRepositoryCommand_whenAllAddedFlagsPassed_thenCreateFullCommand() {
		Map<String,String> inlineValues = new HashMap<String,String>();
		inlineValues.put("image.name", "nginx:1.0.0");
		inlineValues.put("service.type", "clusterIP");
		List<String> valuesFiles = Arrays.asList("file1", "file2");
		
		UpgradeFromAddedRepositoryCommand cmd = new UpgradeFromAddedRepositoryCommand.Builder("test", "nginx", "testrepo")
														.inlineValues(inlineValues)
														.valuesFiles(valuesFiles)
														.version("1.0.0")
														.wait(true)
														.namespace("testns")
														.build();
		
		String expected = "helm upgrade --install test testrepo/nginx --version 1.0.0 --values file1 --values file2 --set service.type=clusterIP --set image.name=nginx:1.0.0 --wait --namespace testns ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUpgradeFromHttpRepositoryCommand_whenNoAddedFlagsPassed_thenCreateMinimumCommand() {
		UpgradeFromHttpRepositoryCommand cmd = new UpgradeFromHttpRepositoryCommand.Builder("test", "nginx", "https://charts.example.com").build();
		String expected = "helm upgrade --install --repo https://charts.example.com test nginx ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUpgradeFromHttpRepositoryCommand_whenAllAddedFlagsPassed_thenCreateFullCommand() {
		Map<String,String> inlineValues = new HashMap<String,String>();
		inlineValues.put("image.name", "nginx:1.0.0");
		inlineValues.put("service.type", "clusterIP");
		List<String> valuesFiles = Arrays.asList("file1", "file2");
		
		UpgradeFromHttpRepositoryCommand cmd = new UpgradeFromHttpRepositoryCommand.Builder("test", "nginx", "https://charts.example.com")
														.inlineValues(inlineValues)
														.valuesFiles(valuesFiles)
														.version("1.0.0")
														.wait(true)
														.namespace("testns")
														.username("user")
														.password("pass")
														.build();
		
		String expected = "helm upgrade --install --repo https://charts.example.com test nginx --version 1.0.0 --username user --password pass --pass-credentials --values file1 --values file2 --set service.type=clusterIP --set image.name=nginx:1.0.0 --wait --namespace testns ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUpgradeFromLocalChartCommand_whenNoAddedFlagsPassed_thenCreateMinimumCommand() {
		UpgradeFromLocalChartCommand cmd = new UpgradeFromLocalChartCommand.Builder("test", "./local-chart").build();
		String expected = "helm upgrade --install test ./local-chart ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUpgradeFromLocalChartCommand_whenAllAddedFlagsPassed_thenCreateFullCommand() {
		Map<String,String> inlineValues = new HashMap<String,String>();
		inlineValues.put("image.name", "nginx:1.0.0");
		inlineValues.put("service.type", "clusterIP");
		List<String> valuesFiles = Arrays.asList("file1", "file2");
		
		UpgradeFromLocalChartCommand cmd = new UpgradeFromLocalChartCommand.Builder("test", "./local-chart")
														.inlineValues(inlineValues)
														.valuesFiles(valuesFiles)
														.wait(true)
														.namespace("testns")
														.build();
		
		String expected = "helm upgrade --install test ./local-chart --values file1 --values file2 --set service.type=clusterIP --set image.name=nginx:1.0.0 --wait --namespace testns ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUpgradeFromOciRegistryCommand_whenNoAddedFlagsPassed_thenCreateMinimumCommand() {
		UpgradeFromOciRegistryCommand cmd = new UpgradeFromOciRegistryCommand.Builder("test", "nginx", "oci://charts.example.com").build();
		String expected = "helm upgrade --install test oci://charts.example.com/nginx ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
	
	@Test
	public void givenUpgradeFromOciRegistryCommand_whenAllAddedFlagsPassed_thenCreateFullCommand() {
		Map<String,String> inlineValues = new HashMap<String,String>();
		inlineValues.put("image.name", "nginx:1.0.0");
		inlineValues.put("service.type", "clusterIP");
		List<String> valuesFiles = Arrays.asList("file1", "file2");
		
		UpgradeFromOciRegistryCommand cmd = new UpgradeFromOciRegistryCommand.Builder("test", "nginx", "oci://charts.example.com")
														.inlineValues(inlineValues)
														.valuesFiles(valuesFiles)
														.version("1.0.0")
														.wait(true)
														.namespace("testns")
														.build();
		
		String expected = "helm upgrade --install test oci://charts.example.com/nginx --version 1.0.0 --values file1 --values file2 --set service.type=clusterIP --set image.name=nginx:1.0.0 --wait --namespace testns ";
		Assert.assertEquals(expected, cmd.createCommand());
	}
}
