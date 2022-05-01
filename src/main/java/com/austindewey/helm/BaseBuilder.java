package com.austindewey.helm;

/**
 * Base builder object
 * Contains the common set of fields for all Helm command builders
 * 
 * @author Austin Dewey
 * @param <T> The type of builder
 */
public abstract class BaseBuilder<T> {
	
	private T builder;

	private String releaseName;
	private String namespace;
	private boolean wait;
	
	public BaseBuilder(String releaseName) {
		this.releaseName = releaseName;
		builder = getBuilder();
	}
	
	abstract T getBuilder();
	
	public T namespace(String namespace) {
		this.namespace = namespace;
		return builder;
	}
	
	public T wait(boolean wait) {
		this.wait = wait;
		return builder;
	}
	
	String getReleaseName() {
		return releaseName;
	}
	
	String getNamespace() {
		return namespace;
	}
	
	boolean getWait() {
		return wait;
	}
}
