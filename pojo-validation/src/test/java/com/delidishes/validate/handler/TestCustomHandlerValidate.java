package com.delidishes.validate.handler;

public class TestCustomHandlerValidate implements IValidateHandler<Integer> {
	@Override
	public boolean verify(Integer value) {
		return value * 2 < 100;
	}
}
