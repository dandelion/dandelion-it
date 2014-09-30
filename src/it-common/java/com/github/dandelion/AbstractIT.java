/*
 * [The "BSD licence"]
 * Copyright (c) 2013-2014 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.dandelion;

import java.io.File;
import java.util.concurrent.TimeUnit;

import net.anthavio.phanbedder.Phanbedder;

import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.dandelion.core.DandelionException;
import com.github.dandelion.core.config.DandelionConfig;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

public abstract class AbstractIT extends Fluent {

	public static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(1024, 768);
	public static final String SERVER_HOST = "127.0.0.1";
	public static final int SERVER_PORT = 9190;

	protected WebDriver driver;

	@BeforeClass
	public static void beforeAll() {
		for (DandelionConfig config : DandelionConfig.values()) {
			System.clearProperty(config.getName());
		}
	}

	@Rule
	public TestWatcher watchman = new TestWatcher() {

		@Override
		protected void starting(Description description) {

			File phantomjs = Phanbedder.unpack();
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
					phantomjs.getAbsolutePath());
			desiredCapabilities.setCapability("phantomjs.page.settings.handlesAlerts", true);
			desiredCapabilities.setCapability("phantomjs.page.settings.localToRemoteUrlAccessEnabled", true);
			desiredCapabilities.setCapability("phantomjs.page.settings.webSecurityEnabled", false);
			desiredCapabilities.setCapability("takesScreenshot", false);
			desiredCapabilities.setCapability("browserConnectionEnabled", true);
			desiredCapabilities.setCapability("locationContextEnabled", true);
			desiredCapabilities.setCapability("applicationCacheEnabled", true);
			driver = new PhantomJSDriver(desiredCapabilities);
			driver.manage().deleteAllCookies();
			driver.manage().window().setSize(DEFAULT_WINDOW_SIZE);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
			initFluent(driver).withDefaultUrl(defaultUrl());
		}

		@Override
		protected void finished(Description description) {
			if (driver != null) {
				driver.quit();
			}
		}
	};

	public String defaultUrl() {
		return "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}

	public String getDefaultBaseUrl() {
		return "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}

	/**
	 * <p>
	 * Build the path to the view to load for an integration test depending on
	 * the kind of test to run.
	 * 
	 * @param page
	 *            The page to load, without any prefix or suffix.
	 */
	public void goToPage(String page) {

		ServerConfig serverConfig = findAnnotation(this.getClass(), ServerConfig.class);

		if (serverConfig == null) {
			throw new DandelionException("The @ServerConfig annotation must be present");
		}

		switch (serverConfig.templateEngine()) {
		case JSP:
			goTo("/" + page);
			break;
		case THYMELEAF:
			goTo("/thymeleaf/" + page);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	protected void goToPage(String page, boolean debug) {
		goToPage(page);
		System.out.println(driver.getPageSource());
	}

	public FluentWebElement getHtmlHead() {
		return findFirst("head");
	}

	public FluentWebElement getHtmlBody() {
		return findFirst("body");
	}
}
