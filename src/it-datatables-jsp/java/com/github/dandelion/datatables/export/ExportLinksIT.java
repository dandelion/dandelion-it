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

package com.github.dandelion.datatables.export;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.AbstractDatatablesIT;
import com.github.dandelion.TemplateEngine;
import com.github.dandelion.core.config.DandelionConfig;
import com.github.dandelion.core.web.WebConstants;
import com.github.dandelion.junit.JettyJUnitRunner;
import com.github.dandelion.junit.JettyJUnitRunner.ServerConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the basic Features of Dandelion-Datatables.
 *
 * @author Thibault Duchateau
 */
@RunWith(JettyJUnitRunner.class)
@ServerConfig(templateEngine = TemplateEngine.JSP, webappBase = AbstractDatatablesIT.DEFAULT_011x_JSP)
public class ExportLinksIT extends AbstractDatatablesIT {

	@BeforeClass
	public static void setup() {
		System.setProperty(DandelionConfig.TOOL_ASSET_PRETTY_PRINTING.getName(), "false");
	}

	@AfterClass
	public static void teardown() {
		System.clearProperty(DandelionConfig.TOOL_ASSET_PRETTY_PRINTING.getName());
	}
	
	@Test
	public void should_generate_default_csv_link() {
		goToPage("export/default_csv_link", true);
		System.out.println(getConfigurationFromPage("export/default_csv_link"));
		assertThat(find("div.dataTables_export")).hasSize(1);
		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("CSV");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("href")).isEqualTo(
				getDefaultBaseUrl() + "/export/default_csv_link.jsp?dtt=f&dtf=csv&dti=myTableId&dtp=y&"
						+ WebConstants.DANDELION_ASSET_FILTER_STATE + "=false");
	}
	
	@Test
	public void should_generate_csv_link_with_one_existing_url_parameter() {
		goTo("/export/default_csv_link.jsp?param1=val1");

		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("CSV");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("href"))
			.isEqualTo(getDefaultBaseUrl() + "/export/default_csv_link.jsp?param1=val1&dtt=f&dtf=csv&dti=myTableId&dtp=y&" + WebConstants.DANDELION_ASSET_FILTER_STATE + "=false");
	}
	
	@Test
	public void should_generate_csv_link_with_multiple_existing_url_parameters() {
		goTo("/export/default_csv_link.jsp?param1=val1&param2=val2");

		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("CSV");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("href"))
			.isEqualTo(getDefaultBaseUrl() + "/export/default_csv_link.jsp?param1=val1&param2=val2&dtt=f&dtf=csv&dti=myTableId&dtp=y&" + WebConstants.DANDELION_ASSET_FILTER_STATE + "=false");
	}
	
	@Ignore
	public void should_generate_custom_csv_link(){
		goToPage("/export/custom_link");
		
		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("myLabel");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("class")).contains("myClass");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("style")).containsIgnoringCase("myStyle;");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("href")).isEqualTo(
				"/export/custom_csv_link.jsp?dtt=1&amp;dti=myTableId");
	}
	
	@Test
	public void should_generate_export_markup() throws Exception {
		goToPage("export/default_csv_link");
		assertThat(find("div.dataTables_export")).hasSize(1);
	}
	
	@Test
	public void should_generate_csv_link_with_custom_url() throws Exception {
		goToPage("export/custom_csv_url");
		goToPage("export/custom_csv_url", true);

		assertThat(find("div.dataTables_export")).hasSize(1);
		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("CSV");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("onclick")).isEqualTo("ddl_dt_launch_export_myTableId_csv();");

		String js = getConfigurationFromPage("export/custom_csv_url");
		assertThat(js).contains("function ddl_dt_launch_export_myTableId_csv(){window.location=\"/context/customCsvUrl");
		assertThat(js).contains(WebConstants.DANDELION_ASSET_FILTER_STATE + "=false");
		assertThat(js).contains("$.param(oTable_myTableId.oApi._fnAjaxParameters(oTable_myTableId.fnSettings()))");
	}
	
	@Test
	public void should_generate_pdf_link_with_custom_url() throws Exception {
		goToPage("export/custom_pdf_url");

		assertThat(find("div.dataTables_export")).hasSize(1);
		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("PDF");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("onclick")).isEqualTo("ddl_dt_launch_export_myTableId_pdf();");

		String js = getConfigurationFromPage("export/custom_pdf_url");
		assertThat(js).contains("function ddl_dt_launch_export_myTableId_pdf(){window.location=\"/context/customPdfUrl");
		assertThat(js).contains(WebConstants.DANDELION_ASSET_FILTER_STATE + "=false");
		assertThat(js).contains("$.param(oTable_myTableId.oApi._fnAjaxParameters(oTable_myTableId.fnSettings()))");
	}
	
	@Test
	public void should_generate_xls_link_with_custom_url() throws Exception {
		goToPage("export/custom_xls_url");

		assertThat(find("div.dataTables_export")).hasSize(1);
		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("XLS");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("onclick")).isEqualTo("ddl_dt_launch_export_myTableId_xls();");

		String js = getConfigurationFromPage("export/custom_xls_url");
		assertThat(js).contains("function ddl_dt_launch_export_myTableId_xls(){window.location=\"/context/customXlsUrl");
		assertThat(js).contains(WebConstants.DANDELION_ASSET_FILTER_STATE + "=false");
		assertThat(js).contains("$.param(oTable_myTableId.oApi._fnAjaxParameters(oTable_myTableId.fnSettings()))");
	}
	
	@Test
	public void should_generate_xlsx_link_with_custom_url() throws Exception {
		goToPage("export/custom_xlsx_url");

		assertThat(find("div.dataTables_export")).hasSize(1);
		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("XLSX");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("onclick")).isEqualTo("ddl_dt_launch_export_myTableId_xlsx();");
		
		String js = getConfigurationFromPage("export/custom_xlsx_url");
		assertThat(js).contains("function ddl_dt_launch_export_myTableId_xlsx(){window.location=\"/context/customXlsxUrl");
		assertThat(js).contains(WebConstants.DANDELION_ASSET_FILTER_STATE + "=false");
		assertThat(js).contains("$.param(oTable_myTableId.oApi._fnAjaxParameters(oTable_myTableId.fnSettings()))");
	}
	
	@Test
	public void should_generate_xml_link_with_custom_url() throws Exception {
		goToPage("export/custom_xml_url");

		assertThat(find("div.dataTables_export")).hasSize(1);
		assertThat(find("div.dataTables_export").findFirst("a").getText()).isEqualTo("XML");
		assertThat(find("div.dataTables_export").findFirst("a").getAttribute("onclick")).isEqualTo("ddl_dt_launch_export_myTableId_xml();");

		String js = getConfigurationFromPage("export/custom_xml_url");
		assertThat(js).contains("function ddl_dt_launch_export_myTableId_xml(){window.location=\"/context/customXmlUrl");
		assertThat(js).contains(WebConstants.DANDELION_ASSET_FILTER_STATE + "=false");
		assertThat(js).contains("$.param(oTable_myTableId.oApi._fnAjaxParameters(oTable_myTableId.fnSettings()))");
	}
}