package com.austindewey.helm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of Helm upgrade when referencing a chart from an OCI registry
 * 
 * @author Austin Dewey
 */
public class UpgradeFromOciRegistryCommand extends BaseUpgradeCommand {
	
	private final String chartName;
	private final String version;
	private final String url;
	
	private Logger log = LoggerFactory.getLogger(UpgradeFromOciRegistryCommand.class);
	
	private UpgradeFromOciRegistryCommand(Builder builder) {
		super(builder.getReleaseName(), builder.getValuesFiles(), builder.getInlineValues(), builder.getWait(), builder.getNamespace());
		this.chartName = builder.chartName;
		this.version = builder.version;
		this.url = builder.url;
	}

	@Override
	String createCommand() {
		String command = String.format("helm upgrade --install %s %s/%s ", releaseName, url, chartName);
		if (version != null) {
			command += String.format("--version %s ", version);
		}
		command += addUpgradeFlags();
		
		log.debug("Helm command: " + command);
		
		return command;
	}
	
	public static class Builder extends BaseUpgradeBuilder<Builder> {
		
		private String chartName;
		private String version;
		private String url;
		
		public Builder(String releaseName, String chartName, String url) {
			super(releaseName);
			this.chartName = chartName;
			this.url = url;
		}
		
		@Override
		Builder getBuilder() {
			return this;
		}
		
		public Builder version(String version) {
			this.version = version;
			return this;
		}
		
		public UpgradeFromOciRegistryCommand build() {
			return new UpgradeFromOciRegistryCommand(this);
		}
	}
}
