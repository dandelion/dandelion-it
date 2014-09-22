package com.company.custom.pack;

import com.github.dandelion.datatables.core.constants.DTConstants;
import com.github.dandelion.datatables.core.extension.AbstractExtension;
import com.github.dandelion.datatables.core.html.HtmlTable;

public class MyCustomFeature extends AbstractExtension {

	@Override
	public String getExtensionName() {
		return "myCustomFeature";
	}

	@Override
	public void setup(HtmlTable table) {
		addParameter(DTConstants.DT_STATE_SAVE, true);
	}
}