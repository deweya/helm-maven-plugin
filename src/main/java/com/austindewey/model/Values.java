package com.austindewey.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Values {

	private List<String> files;
	private Map<String,String> set;
	
	public String getValuesArgs() {
		StringBuilder values = new StringBuilder();
		if (getFiles() != null) {
			for (String file : getFiles()) {
				values.append(String.format("--values %s ", file));
			}
		}
		return values.toString();
	}
	
	public String getSetArgs() {
		StringBuilder set = new StringBuilder();
		if (getSet() != null) {
			for (Map.Entry<String,String> entry : getSet().entrySet()) {
				set.append(String.format("--set %s=\"%s\" ", entry.getKey(), entry.getValue()));
			}
		}
		return set.toString();
	}
}
