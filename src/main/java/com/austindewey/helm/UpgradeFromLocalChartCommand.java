package com.austindewey.helm;

/**
 * An implementation of Helm upgrade when referencing a chart from the local file system
 * 
 * @author Austin Dewey
 */
public class UpgradeFromLocalChartCommand extends BaseUpgradeCommand {

	private final String localPath;
	
	private UpgradeFromLocalChartCommand(Builder builder) {
		super(builder.getReleaseName(), builder.getValuesFiles(), builder.getInlineValues(), builder.getWait(), builder.getNamespace());
		this.localPath = builder.localPath;
	}
	
	@Override
	String createCommand() {
		String command = String.format("helm upgrade --install %s %s ", releaseName, localPath);
		command += addUpgradeFlags();
		
		return command;
	}
	
	public static class Builder extends BaseUpgradeBuilder<Builder> {
		
		private String localPath;
		
		public Builder(String releaseName, String localPath) {
			super(releaseName);
			this.localPath = localPath;
		}
		
		@Override
		Builder getBuilder() {
			return this;
		}
		
		public UpgradeFromLocalChartCommand build() {
			return new UpgradeFromLocalChartCommand(this);
		}
	}
}
