package com.austindewey.helm;

public class UpgradeFromHttpRepositoryCommand extends BaseUpgradeCommand {
	
	private final String chartName;
	private final String version;
	private final String url;
	
	private UpgradeFromHttpRepositoryCommand(Builder builder) {
		super(builder.getReleaseName(), builder.getValuesFiles(), builder.getInlineValues(), builder.getWait(), builder.getNamespace());
		this.chartName = builder.chartName;
		this.version = builder.version;
		this.url = builder.url;
	}
	
	@Override
	String createCommand() {
		String command = String.format("helm upgrade --install --repo %s %s %s ", url, releaseName, chartName);
		if (version != null) {
			command += String.format("--version %s ", version);
		}
		command += addUpgradeFlags();
		
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
		
		public UpgradeFromHttpRepositoryCommand build() {
			UpgradeFromHttpRepositoryCommand command = new UpgradeFromHttpRepositoryCommand(this);
			validate(command);
			return command;
		}
		
		private void validate(UpgradeFromHttpRepositoryCommand command) {
			
		}
	}
}
