package com.delidishes.validate.spring.test.dto;

import com.delidishes.validate.annotation.NotNull;

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
