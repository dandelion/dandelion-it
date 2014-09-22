package com.github.dandelion.datatables;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MockController {

	@RequestMapping("/**/*")
	public void defaultRequest() {
	}
}