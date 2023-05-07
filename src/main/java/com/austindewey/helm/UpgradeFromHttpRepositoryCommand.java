package com.austindewey.helm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private Logger log = LoggerFactory.getLogger(UpgradeFromHttpRepositoryCommand.class);
	
	private UpgradeFromHttpRepositoryCommand(Builder builder) {
		super(builder);
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
		command += addUpgradeFlags();
		
		String maskedCommand = command;
		if (username != null && password != null) {
			command += String.format("--username %s --password %s --pass-credentials ", username, password);
			maskedCommand += String.format("--username %s --password <masked> --pass-credentials ", username);
		}
		
		log.debug("Helm command: " + maskedCommand);
		
		return command;
	}
	
	public static class Builder extends BaseUpgradeBuilder<Builder, UpgradeFromHttpRepositoryCommand> {
		
		private String chartName;
		private String version;
		private String url;
		private String username;
		private String password;
		
		public Builder chartName(String chartName) {
			this.chartName = chartName;
			return this;
		}

		public Builder version(String version) {
			this.version = version;
			return this;
		}

		public Builder url(String url) {
			this.url = url;
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
