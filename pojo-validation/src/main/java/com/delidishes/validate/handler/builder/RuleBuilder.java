package com.delidishes.validate.handler.builder;

import com.delidishes.validate.exception.FieldAccessException;
import com.delidishes.validate.exception.RuleException;
import com.delidishes.validate.handler.pojo.Rule;

import java.lang.reflect.Field;

import static com.delidishes.validate.ValidateStringUtils.isNotEmpty;

public class RuleBuilder {

	private RuleBuilder() {
	}

	public static <T, P> Rule<T> createRule(com.delidishes.validate.annotation.Rule rule, T currentValue, P pojo) {
		Rule<T> r = new Rule<T>();
		r.setOperation(rule.rule());
		r.setCurrentValue(currentValue);
		r.setCompareValue(compareValue(rule, pojo));
		return r;
	}

	private static <T, P> T compareValue(com.delidishes.validate.annotation.Rule rule, P pojo) {
		if (isNotEmpty(rule.field())) {
			return (T) fieldCompareValue(rule.field(), pojo);
		} else if (isNotEmpty(rule.val())) {
			return (T) rule.val();
		} else {
			throw new RuleException("Need fill param @Rule.field or @Rule.val");
		}
	}

	private static <T, P> T fieldCompareValue(String fieldName, P pojo) {
		try {
			Field field = pojo.getClass().getField(fieldName);
			field.setAccessible(true);
			return (T) field.get(pojo);
		} catch (Exception e) {
			throw new FieldAccessException(e.getMessage(), e);
		}
	}

}
