package com.delidishes.validate.exception;

public class FieldAccessException extends RuntimeException {

	public FieldAccessException(String message) {
		super(message);
	}

	public FieldAccessException() {
		super();
	}

	public FieldAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
