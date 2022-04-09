package com.austindewey.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Chart {
	
	private String name;
	private String version;
	private Repository repository;
	private Values values;
	
	public String getValuesArgs() {
		StringBuilder values = new StringBuilder();
		if (getValues() != null && getValues().getFiles() != null) {
			for (String file : getValues().getFiles()) {
				values.append(String.format("--values %s ", file));
			}
		}
		return values.toString();
	}
	
	public String getSetArgs() {
		StringBuilder set = new StringBuilder();
		if (getValues() != null && getValues().getSet() != null) {
			for (Map.Entry<String,String> entry : getValues().getSet().entrySet()) {
				set.append(String.format("--set %s=\"%s\" ", entry.getKey(), entry.getValue()));
			}
		}
		return set.toString();
	}
}
