package com.delidishes.validate;

public class ValidateException extends Exception {

	public ValidateException() {
	}

	public ValidateException(ValidateResult result) {
		super(result.getError());
	}

	public ValidateException(String message) {
		super(message);
	}
}
