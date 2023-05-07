package com.austindewey.helm;

/**
 * Base builder object
 * Contains the common set of fields for all Helm command builders
 * 
 * @author Austin Dewey
 * @param <B> The type of builder
 * @param <C> The type of command
 */
public abstract class BaseBuilder<B extends BaseBuilder<B,C>, C extends BaseCommand> {

	private String releaseName;
	private String namespace;
	private boolean wait;
	private String context;

	public B releaseName(String releaseName) {
		this.releaseName = releaseName;
		return (B)this;
	}

	public B namespace(String namespace) {
		this.namespace = namespace;
		return (B)this;
	}
	
	public B wait(boolean wait) {
		this.wait = wait;
		return (B)this;
	}

	public B context(String  context) {
		this.context = context;
		return (B)this;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public String getNamespace() {
		return namespace;
	}

	public boolean getWait() {
		return wait;
	}

	public String getContext() {
		return context;
	}

	public abstract C build();
}
