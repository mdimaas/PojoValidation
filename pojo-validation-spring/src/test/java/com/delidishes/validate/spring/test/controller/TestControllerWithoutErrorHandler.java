package com.delidishes.validate.spring.test.controller;


import com.delidishes.validate.spring.ValidType;
import com.delidishes.validate.spring.annotation.Valid;
import com.delidishes.validate.spring.test.dto.TestPojo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestControllerWithoutErrorHandler {

	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String test(@Valid(type = ValidType.NOT_NULL) @RequestBody TestPojo test){
		return "testSuccess";
	}

	@RequestMapping(value = "/testGoogleCaptcha2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String testGoogleCaptcha2(@Valid(type = ValidType.GOOGLE_RECAPTCHA2) @RequestBody TestPojo test){
		return "testSuccess";
	}

	@RequestMapping(value = "/testRules", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String testRules(@Valid(type = ValidType.RULES) @RequestBody TestPojo test){
		return "testSuccess";
	}

	@RequestMapping(value = "/testCustomHandler", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String testCustomHandler(@Valid(type = ValidType.CUSTOM) @RequestBody TestPojo test){
		return "testSuccess";
	}

	@RequestMapping(value = "/testWithoutValidation", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String testWithoutValidation(@RequestBody TestPojo test){
		return test.getTest();
	}
}
