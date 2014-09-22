package com.github.dandelion.datatables.extra.spring3;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.AbstractDatatablesIT;
import com.github.dandelion.TemplateEngine;
import com.github.dandelion.datatables.core.constants.SystemConstants;
import com.github.dandelion.datatables.core.i18n.MessageResolver;
import com.github.dandelion.junit.JettyJUnitRunner;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.JSP, webappBase = MessageIT.WEBAPP_BASE)
public class MessageIT extends AbstractDatatablesIT {

	public static final String WEBAPP_BASE = "src/it-jsp-datatables/resources/webapp-spring3-messages";
	
	@BeforeClass
	public static void setup() {
		String path = new File(WEBAPP_BASE + "/").getAbsolutePath();
		System.setProperty(SystemConstants.DANDELION_DT_CONFIGURATION, path);
	}

	@AfterClass
	public static void tearDown() {
		System.clearProperty(SystemConstants.DANDELION_DT_CONFIGURATION);
	}

	@Test
	public void should_display_column_headers_from_spring_message_source() {
		goTo("/");
		System.out.println(driver.getPageSource());
		assertThat(getWrapper()).isNotNull();
		assertThat(getTableHead().findFirst("th").getText()).isEqualTo("IdFromProp");
		assertThat(getTableHead().find("th", 1).getText()).isEqualTo(MessageResolver.UNDEFINED_KEY + "id.firstName" + MessageResolver.UNDEFINED_KEY);
		assertThat(getTableHead().find("th", 2).getText()).isEqualTo("LastName");
	}
}