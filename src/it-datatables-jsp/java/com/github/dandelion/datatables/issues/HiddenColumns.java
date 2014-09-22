package com.github.dandelion.datatables.issues;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.AbstractDatatablesIT;
import com.github.dandelion.junit.JettyJUnitRunner;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;
import com.github.dandelion.TemplateEngine;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * See the related issues:
 * <ul>
 * <li><a href=
 * "http://dandelion-forum.48353.x6.nabble.com/Is-there-any-way-to-store-hidden-fields-in-Datatables-td686.html"
 * >http://dandelion-forum.48353.x6.nabble.com/Is-there-any-way-to-store-hidden-
 * fields-in-Datatables-td686.html</a></li>
 * </ul>
 * 
 * @author Thibault Duchateau
 */
@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.JSP, webappBase = AbstractDatatablesIT.DEFAULT_011x_JSP)
public class HiddenColumns extends AbstractDatatablesIT {

	@Test
	public void should_not_generate_markup_for_hidden_columns() {
		goToPage("issues/hidden_columns", true);

		assertThat(getTable().find("tbody").find("tr", 0).find("td")).hasSize(4);
	}
}