package com.austindewey.helm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of Helm upgrade when referencing a chart from a locally added repository (from <i>helm repo add</i>)
 * 
 * @author Austin Dewey
 */
public class UpgradeFromAddedRepositoryCommand extends BaseUpgradeCommand {

	private final String chartName;
	private final String version;
	private final String repositoryName;
	
	private Logger log = LoggerFactory.getLogger(UpgradeFromAddedRepositoryCommand.class);
	
	public UpgradeFromAddedRepositoryCommand(Builder builder) {
		super(builder);
		this.chartName = builder.chartName;
		this.version = builder.version;
		this.repositoryName = builder.repositoryName;
	}
	
	@Override
	String createCommand() {
		String command = String.format("helm upgrade --install %s %s/%s ", releaseName, repositoryName, chartName);
		if (version != null) {
			command += String.format("--version %s ", version);
		}
		command += addUpgradeFlags();
		
		log.debug("Helm command: " + command);
		
		return command;
	}
	
	public static class Builder extends BaseUpgradeBuilder<Builder, UpgradeFromAddedRepositoryCommand> {
		
		private String chartName;
		private String version;
		private String repositoryName;
		
		public Builder chartName(String chartName) {
			this.chartName = chartName;
			return this;
		}

		public Builder version(String version) {
			this.version = version;
			return this;
		}

		public Builder repositoryName(String repositoryName) {
			this.repositoryName = repositoryName;
			return this;
		}

		public UpgradeFromAddedRepositoryCommand build() {
			return new UpgradeFromAddedRepositoryCommand(this);
		}
	}
}
