package com.austindewey.helm;

/**
 * Types of Helm commands
 * 
 * @author Austin Dewey
 *
 */
public enum CommandType {
	UPGRADE_FROM_HTTP_REPOSITORY,
	UPGRADE_FROM_OCI_REGISTRY,
	UPGRADE_FROM_LOCAL,
	UPGRADE_FROM_ADDED_REPOSITORY,
	UNINSTALL
}
