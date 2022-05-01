package com.austindewey.helm;

/**
 * The Helm uninstall implementation
 * 
 * @author Austin Dewey
 */
public class UninstallCommand extends BaseCommand {

	private UninstallCommand(Builder builder) {
		super(builder.getReleaseName(), builder.getNamespace(), builder.getWait());
	}
	
	@Override
	String createCommand() {
		String command = String.format("helm uninstall %s ", releaseName);
		command += addCommonFlags();
		
		return command;
	}
	
	public static class Builder extends BaseBuilder<Builder> {
		
		public Builder(String releaseName) {
			super(releaseName);
		}
		
		@Override
		Builder getBuilder() {
			return this;
		}
		
		public UninstallCommand build() {
			UninstallCommand command = new UninstallCommand(this);
			validate(command);
			return command;
		}
		
		private void validate(UninstallCommand command) {
			
		}
	}
}
