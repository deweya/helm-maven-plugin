package com.austindewey.helm;

import java.util.List;
import java.util.Map;

public abstract class BaseUpgradeCommand extends BaseCommand {

	final List<String> valuesFiles;
	final Map<String,String> inlineValues;
	
	public BaseUpgradeCommand(String releaseName, List<String> valuesFiles, Map<String,String> inlineValues, boolean wait, String namespace) {
		super(releaseName, namespace, wait);
		this.valuesFiles = valuesFiles;
		this.inlineValues = inlineValues;
	}
	
	String addUpgradeFlags() {
		String flags = "";
		
		if (valuesFiles != null) {
			for (String file : valuesFiles) {
				flags += String.format("--values %s ", file);
			}
		}
		if (inlineValues != null) {
			for (Map.Entry<String,String> entry : inlineValues.entrySet()) {
				flags += String.format("--set %s=%s ", entry.getKey(), entry.getValue());
			}
		}
		
		flags += addCommonFlags();
		
		return flags;
	}
}
