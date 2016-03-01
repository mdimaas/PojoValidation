package com.delidishes.validate.spring.test.controller;

import com.delidishes.validate.exception.ValidateException;
import com.delidishes.validate.spring.annotation.Valid;
import com.delidishes.validate.spring.test.dto.TestPojo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestControllerWithErrorHandler {

	@RequestMapping(value = "/test2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String validateTest(@Valid @RequestBody TestPojo testPojo) {
		return testPojo.getTest();
	}

	@ExceptionHandler(value = ValidateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String errorHandler(ValidateException ex){
		return ex.getMessage();
	}
}
