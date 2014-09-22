package com.github.dandelion.datatables.html;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.AbstractDatatablesIT;
import com.github.dandelion.junit.JettyJUnitRunner;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;
import com.github.dandelion.TemplateEngine;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the HTML markup generation with custom caption tag.
 *
 * @author Gautier Dhordain
 */
@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.JSP, webappBase = AbstractDatatablesIT.DEFAULT_011x_JSP)
public class CaptionTagIT extends AbstractDatatablesIT {

	@Test
	public void should_not_generate_caption_tag_by_default() {
		goToPage("html/dom/table");

		assertThat(getTable().find("caption")).hasSize(0);
	}

	@Test
	public void should_generate_caption_tag() {
		goToPage("html/table_with_caption");

		assertThat(getTable().find("caption")).hasSize(1);
	}

	@Test
	public void should_populate_default_HTML_attribute() {
		goToPage("html/table_with_caption");

		assertThat(getTable().findFirst("caption").getId()).isEqualTo("captionId");
		assertThat(getTable().findFirst("caption").getAttribute("class")).isEqualTo("captionCssClass");
//		assertThat(getTable().findFirst("caption").getAttribute("style")).isEqualTo("captionCssStyle");
		assertThat(getTable().findFirst("caption").getAttribute("title")).isEqualTo("captionTitle");
	}

	@Test
	public void should_populate_caption_value_with_text() {
		goToPage("html/table_with_caption");

		assertThat(getTable().find("caption").getText()).isEqualTo("captionValue");
	}

	@Test
	public void should_populate_caption_value_with_evaluated_tag() {
		goToPage("html/table_with_dynamic_caption");
		System.out.println(driver.getPageSource());
		assertThat(getTable().find("caption").getText()).isEqualTo("dynamic with JSP tag");
	}
}