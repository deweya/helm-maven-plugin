package com.austindewey.model;

import java.util.List;
import java.util.Map;

/**
 * Represents Helm values files and inline values to be applied with the Helm chart
 * 
 * @author Austin Dewey
 *
 */
public class Values {

	private List<String> files;
	private Map<String,String> set;
	
	public Values() {}
	
	public Values(List<String> files, Map<String,String> set) {
		this.files = files;
		this.set = set;
	}
	
	public List<String> getFiles() {
		return files;
	}
	
	public void setFiles(List<String> files) {
		this.files = files;
	}
	
	public Map<String,String> getSet() {
		return set;
	}
	
	public void setSet(Map<String,String> set) {
		this.set = set;
	}
}
