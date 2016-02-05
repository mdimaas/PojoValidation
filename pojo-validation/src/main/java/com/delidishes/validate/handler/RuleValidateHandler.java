package com.delidishes.validate.handler;

import com.delidishes.validate.handler.pojo.Rule;

public class RuleValidateHandler<T> implements IValidateHandler<Rule<T>> {
	@Override
	public boolean verify(Rule<T> value) {
		switch (value.getOperation()) {
			case EQ:
				return value.getCurrentValue() != null && value.getCurrentValue().equals(value.getCompareValue());
			case NOTEQ:
				return value.getCurrentValue() != null && !value.getCurrentValue().equals(value.getCompareValue());
			default:
				throw new UnsupportedOperationException("Unsupported operation " + value.getOperation());
		}
	}


}
