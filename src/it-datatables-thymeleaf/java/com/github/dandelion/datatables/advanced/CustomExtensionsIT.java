package com.github.dandelion.datatables.advanced;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.AbstractDatatablesIT;
import com.github.dandelion.TemplateEngine;
import com.github.dandelion.core.config.DandelionConfig;
import com.github.dandelion.junit.JettyJUnitRunner;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the enablement of custom extensions.
 *
 * @author Thibault Duchateau
 */
@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.THYMELEAF, webappBase = AbstractDatatablesIT.DEFAULT_011x_THYMELEAF)
public class CustomExtensionsIT extends AbstractDatatablesIT {

	@BeforeClass
	public static void setup() {
		System.setProperty(DandelionConfig.TOOL_ASSET_PRETTY_PRINTING.getName(), "false");
	}

	@AfterClass
	public static void teardown() {
		System.clearProperty(DandelionConfig.TOOL_ASSET_PRETTY_PRINTING.getName());
	}
	
	@Test
	public void should_enable_myCustomFeature() {
		goToPage("advanced/custom-extension/custom_extensions");
		assertThat(getConfigurationFromPage("advanced/custom-extension/custom_extensions")).contains("\"bStateSave\":true");
	}
	
	@Test
	public void should_enable_myCustomFeature_and_myOtherCustomFeature() {
		goToPage("advanced/custom-extension/custom_extensions2");
		assertThat(getConfigurationFromPage("advanced/custom-extension/custom_extensions2")).contains("\"bStateSave\":true");
		assertThat(getConfigurationFromPage("advanced/custom-extension/custom_extensions2")).contains("\"bAutoWidth\":true");
	}
}