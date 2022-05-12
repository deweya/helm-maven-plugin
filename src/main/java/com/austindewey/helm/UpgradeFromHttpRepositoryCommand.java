package com.austindewey.helm;

/**
 * An implementation of Helm upgrade when referencing a chart from an HTTP(s) repository
 * 
 * @author Austin Dewey
 */
public class UpgradeFromHttpRepositoryCommand extends BaseUpgradeCommand {
	
	private final String chartName;
	private final String version;
	private final String url;
	private final String username;
	private final String password;
	
	private UpgradeFromHttpRepositoryCommand(Builder builder) {
		super(builder.getReleaseName(), builder.getValuesFiles(), builder.getInlineValues(), builder.getWait(), builder.getNamespace());
		this.chartName = builder.chartName;
		this.version = builder.version;
		this.url = builder.url;
		this.username = builder.username;
		this.password = builder.password;
	}
	
	@Override
	String createCommand() {
		String command = String.format("helm upgrade --install --repo %s %s %s ", url, releaseName, chartName);
		if (version != null) {
			command += String.format("--version %s ", version);
		}
		if (username != null && password != null) {
			command += String.format("--username %s --password %s --pass-credentials ", username, password);
		}
		command += addUpgradeFlags();
		
		return command;
	}
	
	public static class Builder extends BaseUpgradeBuilder<Builder> {
		
		private String chartName;
		private String version;
		private String url;
		private String username;
		private String password;
		
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
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public UpgradeFromHttpRepositoryCommand build() {
			return new UpgradeFromHttpRepositoryCommand(this);
		}
	}
}
