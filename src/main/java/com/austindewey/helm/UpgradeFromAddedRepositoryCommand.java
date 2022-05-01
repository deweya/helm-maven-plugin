package com.austindewey.helm;

/**
 * An implementation of Helm upgrade when referencing a chart from a locally added repository (from <i>helm repo add</i>)
 * 
 * @author Austin Dewey
 */
public class UpgradeFromAddedRepositoryCommand extends BaseUpgradeCommand {

	private final String chartName;
	private final String version;
	private final String repositoryName;
	
	public UpgradeFromAddedRepositoryCommand(Builder builder) {
		super(builder.getReleaseName(), builder.getValuesFiles(), builder.getInlineValues(), builder.getWait(), builder.getNamespace());
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
		
		return command;
	}
	
	public static class Builder extends BaseUpgradeBuilder<Builder> {
		
		private String chartName;
		private String version;
		private String repositoryName;
		
		public Builder(String releaseName, String chartName, String repositoryName) {
			super(releaseName);
			this.chartName = chartName;
			this.repositoryName = repositoryName;
		}
		
		@Override
		Builder getBuilder() {
			return this;
		}
		
		public Builder version(String version) {
			this.version = version;
			return this;
		}
		
		public UpgradeFromAddedRepositoryCommand build() {
			return new UpgradeFromAddedRepositoryCommand(this);
		}
	}
}
