package com.austindewey.helm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of Helm upgrade when referencing a chart from the local file system
 * 
 * @author Austin Dewey
 */
public class UpgradeFromLocalChartCommand extends BaseUpgradeCommand {

	private final String localPath;
	
	private Logger log = LoggerFactory.getLogger(UpgradeFromLocalChartCommand.class);
	
	private UpgradeFromLocalChartCommand(Builder builder) {
		super(builder);
		this.localPath = builder.localPath;
	}
	
	@Override
	String createCommand() {
		String command = String.format("helm upgrade --install %s %s ", releaseName, localPath);
		command += addUpgradeFlags();
		
		log.debug("Helm command: " + command);
		
		return command;
	}
	
	public static class Builder extends BaseUpgradeBuilder<Builder, UpgradeFromLocalChartCommand> {
		
		private String localPath;

		public Builder localPath(String localPath) {
			this.localPath = localPath;
			return this;
		}

		public UpgradeFromLocalChartCommand build() {
			return new UpgradeFromLocalChartCommand(this);
		}
	}
}
