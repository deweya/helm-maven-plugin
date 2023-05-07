package com.austindewey.helm;

import java.util.List;
import java.util.Map;

/**
 * The base Helm upgrade command class
 * 
 * @author Austin Dewey
 *
 */
public abstract class BaseUpgradeCommand extends BaseCommand {

	/**
	 * A list of values files
	 */
	final List<String> valuesFiles;
	
	/**
	 * A mapping of inline values (Helm's --set flag)
	 */
	final Map<String,String> inlineValues;
	
	public BaseUpgradeCommand(BaseUpgradeBuilder<?, ? extends BaseUpgradeCommand> builder) {
		super(builder);
		this.valuesFiles = builder.getValuesFiles();
		this.inlineValues = builder.getInlineValues();
	}
	
	/**
	 * A common set of Helm upgrade flags
	 * 
	 * @return Common Helm upgrade flags
	 */
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
