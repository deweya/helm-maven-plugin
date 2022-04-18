package com.austindewey.helm;

import java.util.List;
import java.util.Map;

public abstract class BaseUpgradeBuilder<T> extends BaseBuilder<T> {
	
	private T builder;

	private List<String> valuesFiles;
	private Map<String,String> inlineValues;
	
	public BaseUpgradeBuilder(String releaseName) {
		super(releaseName);
		builder = getBuilder();
	}
	
	public T valuesFiles(List<String> valuesFiles) {
		this.valuesFiles = valuesFiles;
		return builder;
	}
	
	List<String> getValuesFiles() {
		return valuesFiles;
	}
	
	public T inlineValues(Map<String,String> inlineValues) {
		this.inlineValues = inlineValues;
		return builder;
	}
	
	Map<String,String> getInlineValues() {
		return inlineValues;
	}
}
