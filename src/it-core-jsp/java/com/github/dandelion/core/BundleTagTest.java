package com.github.dandelion.core;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.AbstractCoreIT;
import com.github.dandelion.junit.JettyJUnitRunner;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;
import com.github.dandelion.TemplateEngine;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.JSP, webappBase = AbstractCoreIT.DEFAULT_011x_JSP)
public class BundleTagTest extends AbstractCoreIT {

	@Test
	public void should_include_1_bundle() {
		goToPage("include_bundle");
		assertThat(text("link")).hasSize(1);
		assertThat(text("script")).hasSize(2);
	}

	@Test
	public void should_exclude_1_bundle() {
		goToPage("exclude_bundle");
		assertThat(text("script")).hasSize(0);
	}
}
