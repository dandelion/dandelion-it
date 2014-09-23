package com.github.dandelion.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ITController {

	@RequestMapping("/**/*")
	public void defaultRequest() {
	}
}