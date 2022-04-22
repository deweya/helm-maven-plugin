package com.austindewey.helm;

public class UpgradeFromOciRegistryCommand extends BaseUpgradeCommand {
	
	private final String chartName;
	private final String version;
	private final String url;
	
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
			UpgradeFromOciRegistryCommand command = new UpgradeFromOciRegistryCommand(this);
			validate(command);
			return command;
		}
		
		private void validate(UpgradeFromOciRegistryCommand command) {
			
		}
	}
}
