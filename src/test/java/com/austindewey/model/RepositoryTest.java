package com.austindewey.model;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RepositoryTest {

	@Test
	public void givenRepository_whenNameAndUrlAreNull_thenRepositoryIsNotValid() {
		Repository repo = new Repository(null, null, null, null);
		Assertions.assertThrows(MojoExecutionException.class, () -> {
			repo.validate();
		});
	}
	
	@Test
	public void givenRepository_whenUsernameIsProvidedButPasswordIsNullOrViceVersa_thenRepositoryIsNotValid() {
		Repository repo = new Repository(null, "https://example.com", "username", null);
		Assertions.assertThrows(MojoExecutionException.class, () -> {
			repo.validate();
		});
		
		Repository repo2 = new Repository(null, "https://example.com", null, "password");
		Assertions.assertThrows(MojoExecutionException.class, () -> {
			repo2.validate();
		});
	}
}
