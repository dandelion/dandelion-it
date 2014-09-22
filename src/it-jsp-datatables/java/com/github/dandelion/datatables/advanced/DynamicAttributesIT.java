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
package com.github.dandelion.datatables.advanced;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.AbstractDatatablesIT;
import com.github.dandelion.junit.JettyJUnitRunner;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;
import com.github.dandelion.TemplateEngine;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the dynamic attributes.
 *
 * @author Thibault Duchateau
 * @since 0.9.0
 */
@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.JSP, webappBase = AbstractDatatablesIT.DEFAULT_011x_JSP)
public class DynamicAttributesIT extends AbstractDatatablesIT {

	@Test
	public void should_accept_allowed_dynamic_attributes(){
		goToPage("advanced/dynamic_allowed_attributes");
		
		assertThat(getTable().getAttribute("name")).isEqualTo("myName");
		assertThat(getTable().getAttribute("border")).isEqualTo("2px solid black");
	}
	
	@Test
	public void should_not_accept_class_as_a_dynamic_attribute(){
		goToPage("advanced/dynamic_disallowed_class_attribute");
		
		assertThat(find("#" + TABLE_ID + "_wrapper")).hasSize(0);
		assertThat(driver.getPageSource())
				.contains(
						"java.lang.IllegalArgumentException: The 'class' attribute is not allowed. Please use the 'cssClass' attribute instead.");
	}
	
	@Test
	public void should_not_accept_style_as_a_dynamic_attribute(){
		goToPage("advanced/dynamic_disallowed_style_attribute");
		
		assertThat(find("#" + TABLE_ID + "_wrapper")).hasSize(0);
		assertThat(driver.getPageSource())
				.contains(
						"java.lang.IllegalArgumentException: The 'style' attribute is not allowed. Please use the 'cssStyle' attribute instead.");
	}
}