package com.github.dandelion.mock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {

	@RequestMapping(value = "/persons", produces = "application/json")
	public @ResponseBody
	List<Person> findAll(HttpServletRequest request) {
		return Mock.persons;
	}
}