package com.austindewey.helm;

import java.util.List;
import java.util.Map;

/**
 * A builder object used to build different Helm upgrade commands
 * 
 * @author Austin Dewey
 * @param <B> The type of Helm upgrade builder
 * @param <C> The type of Helm upgrade command
 */
public abstract class BaseUpgradeBuilder<B extends BaseBuilder<B,C>, C extends BaseUpgradeCommand> extends BaseBuilder<B,C> {

	private List<String> valuesFiles;
	private Map<String,String> inlineValues;
	
	public B valuesFiles(List<String> valuesFiles) {
		this.valuesFiles = valuesFiles;
		return (B) this;
	}

	public List<String> getValuesFiles() {
		return valuesFiles;
	}

	public B inlineValues(Map<String,String> inlineValues) {
		this.inlineValues = inlineValues;
		return (B) this;
	}

	public Map<String,String> getInlineValues() {
		return inlineValues;
	}
}
