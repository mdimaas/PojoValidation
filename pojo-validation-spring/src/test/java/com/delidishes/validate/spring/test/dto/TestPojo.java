package com.delidishes.validate.spring.test.dto;

import javax.validation.constraints.NotNull;

public class TestPojo {
	@NotNull
	private String test;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
