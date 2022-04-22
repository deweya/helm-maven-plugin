package com.austindewey.model;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RepositoryTest {

	@Test
	public void givenRepository_whenNameAndUrlAreNull_thenRepositoryIsNotValid() {
		Repository repo = new Repository(null, null);
		Assertions.assertThrows(MojoExecutionException.class, () -> {
			repo.validate();
		});
	}
}
