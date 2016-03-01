package com.delidishes.validate.exception;

import com.delidishes.validate.ValidateResult;

public class ValidateException extends Exception {

	public ValidateException() {
		super();
	}

	public ValidateException(ValidateResult result) {
		super(result.getError());
	}

	public ValidateException(String message) {
		super(message);
	}
}
