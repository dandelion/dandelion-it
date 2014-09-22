package com.github.dandelion.datatables.ajax;

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
 * Test the HTML markup generation using an AJAX source and other features in
 * the same table.
 * 
 * @author Thibault Duchateau
 */
@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.JSP, webappBase = AbstractDatatablesIT.DEFAULT_011x_JSP)
public class MultipleFeaturesIT extends AbstractDatatablesIT {

	@BeforeClass
	public static void setup() {
		System.setProperty(DandelionConfig.TOOL_ASSET_PRETTY_PRINTING.getName(), "false");
	}

	@AfterClass
	public static void teardown() {
		System.clearProperty(DandelionConfig.TOOL_ASSET_PRETTY_PRINTING.getName());
	}

	@Test
	public void should_generate_table_with_ajax_source_and_callback() {
		goToPage("ajax/table_with_callback");
		String js = getConfigurationFromPage("ajax/table_with_callback");
		assertThat(js)
				.contains(
						"\"fnInitComplete\":function(oSettings,json){oTable_myTableId.fnAdjustColumnSizing(true);myInitCallback(oSettings,json);},");
	}
}
