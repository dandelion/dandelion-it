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

import java.io.IOException;
import java.net.URL;

import org.fluentlenium.core.domain.FluentWebElement;
import org.springframework.core.annotation.AnnotationUtils;

import com.github.dandelion.core.DandelionException;
import com.github.dandelion.core.asset.Asset;
import com.github.dandelion.core.asset.AssetType;
import com.github.dandelion.core.asset.cache.AssetCacheManager;
import com.github.dandelion.core.utils.ResourceUtils;
import com.github.dandelion.core.web.DandelionServlet;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;

/**
 * <p>
 * Abstract superclass of all integration tests of the Dandelion-Datatables
 * component.
 * </p>
 * 
 * @author Thibault Duchateau
 * @since 0.11.0
 */
public abstract class AbstractDatatablesIT extends AbstractIT {

	public static final String DEFAULT_011x_JSP = "src/it-datatables-jsp/resources/webapp-default";
	public static final String DEFAULT_011x_THYMELEAF = "src/it-datatables-thymeleaf/resources/webapp-default";

	public static final String TABLE_ID = "myTableId";
	public static final String TABLE_ID2 = "mySecondTableId";

	public FluentWebElement getWrapper() {
		return findFirst("#" + TABLE_ID + "_wrapper");
	}

	public FluentWebElement getTable() {
		return getWrapper().findFirst("table");
	}

	public FluentWebElement getTableHead() {
		return getTable().findFirst("thead");
	}

	public FluentWebElement getTableBody() {
		return getTable().findFirst("tbody");
	}

	public FluentWebElement getTableFoot() {
		return getTable().findFirst("tfoot");
	}

	@Override
	protected void goToPage(String page, boolean display) {

		goToPage(page);
		if (display) {
			System.out.println("Source:" + driver.getPageSource());
			System.out.println("Conf:" + getConfigurationFromPage(page));
		}
	}

	public String getConfigurationFromPage(String page) {

		ServerConfig serverConfig = AnnotationUtils.findAnnotation(this.getClass(), ServerConfig.class);

		if (serverConfig == null) {
			throw new DandelionException("The @ServerConfig annotation must be present");
		}

		AssetCacheManager cacheManager = new AssetCacheManager(null);
		Asset asset = new Asset("dandelion-datatables", "0.10.0", AssetType.js);
		String cacheKey = null;

		switch (serverConfig.templateEngine()) {
		case JSP:
			cacheKey = cacheManager.generateCacheKey("http://" + SERVER_HOST + ":" + SERVER_PORT + "/" + page,
					asset);
			break;
		case THYMELEAF:
			cacheKey = cacheManager.generateCacheKey(
					"http://" + SERVER_HOST + ":" + SERVER_PORT + "/thymeleaf/" + page, asset);
			break;
		default:
			break;

		}

		String url = "http://" + SERVER_HOST + ":" + SERVER_PORT + DandelionServlet.DANDELION_ASSETS_URL + cacheKey;
		return getContentFromUrl(url);
	}

	private String getContentFromUrl(String url) {
		try {
			URL urlLocation = new URL(url);
			return ResourceUtils.getContentFromInputStream(urlLocation.openStream());
		}
		catch (IOException e) {
			StringBuilder sb = new StringBuilder("The content pointed by the url ");
			sb.append(url);
			sb.append(" can't be read.");
			throw new DandelionException(sb.toString(), e);
		}
	}
}
