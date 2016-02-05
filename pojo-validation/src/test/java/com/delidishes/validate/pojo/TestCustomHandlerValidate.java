package com.delidishes.validate.pojo;

import com.delidishes.validate.handler.IValidateHandler;

public class TestCustomHandlerValidate implements IValidateHandler<Integer> {
	@Override
	public boolean verify(Integer value) {
		return value * 2 < 100;
	}
}
