package com.github.dandelion.datatables.extra.spring3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Spring3Controller {

	@RequestMapping(value = "/")
	public String goToIndex() {
		return "index";
	}
}