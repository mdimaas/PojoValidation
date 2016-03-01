package com.delidishes.validate;

import com.delidishes.validate.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.delidishes.validate.ValidateStringUtils.NEW_LINE;

public class ValidateResult {
	private boolean isValid = false;
	private List<String> errors = new ArrayList<>();

	public ValidateResult(boolean isValid) {
		this.isValid = isValid;
	}

	public String getErrorsByMapped(Function<String, String> mapped) {
		return errors.stream().filter(ValidateStringUtils::isNotEmpty).map(mapped).collect(Collectors.joining(NEW_LINE));
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public boolean isValid() {
		return isValid;
	}

	public void addError(String error) {
		errors.add(error);
	}

	public List<String> getErrors() {
		return errors;
	}

	public String getError() {
		return errors.stream().filter(ValidateStringUtils::isNotEmpty).collect(Collectors.joining(NEW_LINE));
	}

	public void throwsExceptionIfFalse() throws ValidateException {
		if(!this.isValid()){
			throw new ValidateException(this);
		}
	}

	public void throwsExceptionIfFalse(Function<String, String> mappedErrorText) throws ValidateException {
		if(!this.isValid()){
			throw new ValidateException(this.getErrorsByMapped(mappedErrorText));
		}
	}

}
