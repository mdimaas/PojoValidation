package com.delidishes.validate.spring.test.controller;


import com.delidishes.validate.spring.annotation.Valid;
import com.delidishes.validate.spring.test.dto.TestPojo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestControllerWithoutErrorHandler {

	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String test(@Valid @RequestBody TestPojo test){
		return "testSuccess";
	}
}
