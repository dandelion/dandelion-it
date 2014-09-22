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

package com.github.dandelion.datatables.basics;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.AbstractDatatablesIT;
import com.github.dandelion.TemplateEngine;
import com.github.dandelion.core.utils.StringUtils;
import com.github.dandelion.junit.JettyJUnitRunner;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the CSS attributes.
 *
 * @author Thibault Duchateau
 */
@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.JSP, webappBase = AbstractDatatablesIT.DEFAULT_011x_JSP)
public class CssIT extends AbstractDatatablesIT {

	@Test
	public void should_apply_css_stripe_classes_using_dom_jsp() {
		goToPage("basics/css_stripe_classes", true);

		assertThat(getTable().find("tbody").find("tr", 0).getAttribute("class")).isEqualTo("class1");
		assertThat(getTable().find("tbody").find("tr", 1).getAttribute("class")).isEqualTo("class2");
		assertThat(getTable().find("tbody").find("tr", 2).getAttribute("class")).isEqualTo("class1");
		assertThat(getTable().find("tbody").find("tr", 3).getAttribute("class")).isEqualTo("class2");
	}
	
	@Test
	public void should_apply_css_using_dom() {
		goToPage("basics/css_dom");

		assertThat(getTable().find("thead").find("th", 0).getAttribute("class")).contains("column1class");
		assertThat(getTable().find("thead").find("th", 1).getAttribute("class")).contains("column2class");
		assertThat(StringUtils.trimAllWhitespace(getTable().find("thead").find("th", 1).getAttribute("style"))).contains("text-align:center;");
		assertThat(StringUtils.trimAllWhitespace(getTable().find("thead").find("th", 2).getAttribute("style"))).contains("text-align:center;");
		
		for(int i=0 ; i<10 ; i++){
			assertThat(StringUtils.trimAllWhitespace(getTable().find("tbody").find("tr", i).find("td", 3).getAttribute("class"))).isEqualTo("cityClass");
		}
	}
	
	@Test
	public void should_apply_css_using_ajax() {
		goToPage("basics/css_ajax");

		assertThat(getTable().find("thead").findFirst("th").getAttribute("class")).contains("column1class");
		assertThat(getTable().find("thead").find("th", 1).getAttribute("class")).contains("column2class");
		assertThat(StringUtils.trimAllWhitespace(getTable().find("thead").find("th", 1).getAttribute("style"))).contains("text-align:center;");
		assertThat(StringUtils.trimAllWhitespace(getTable().find("thead").find("th", 2).getAttribute("style"))).contains("text-align:center;");
	}

}