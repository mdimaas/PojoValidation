package com.delidishes.validate.handler.builder;

import com.delidishes.validate.exception.FieldAccessException;
import com.delidishes.validate.exception.RuleException;
import com.delidishes.validate.handler.pojo.Rule;
import com.delidishes.validate.handler.pojo.RuleOperation;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.delidishes.validate.ValidateStringUtils.EMPTY;
import static com.delidishes.validate.ValidateStringUtils.SPACE;

public class RuleBuilder {

	private static final String OPERATION_PATTERN_REGEX = "^([a-zA-Z\\s]+)\\s#f.*$";
	private static final Pattern OPERATION_PATTERN = Pattern.compile(OPERATION_PATTERN_REGEX);
	private static final String FIELD_PATTERN_REGEX = "^.*#f\\{([a-zA-Z\\d]+)\\}$";
	private static final Pattern FIELD_PATTERN = Pattern.compile(FIELD_PATTERN_REGEX);

	private RuleBuilder() {
	}

	public static <T, P> Rule<T> createRule(String ruleStr, T currentValue, P pojo) {
		Rule<T> rule = new Rule<T>();
		rule.setOperation(parseRuleOperation(ruleStr));
		rule.setCurrentValue(currentValue);
		rule.setCompareValue(parseRuleCompareValue(ruleStr, pojo));
		return rule;
	}

	private static <T, P> T parseRuleCompareValue(String ruleStr, P pojo) {
		try {
			Field field = pojo.getClass().getField(parseFieldName(ruleStr));
			field.setAccessible(true);
			return (T) field.get(pojo);
		} catch (Exception e) {
			throw new FieldAccessException(e.getMessage(), e);
		}
	}

	private static String parseFieldName(String expression) {
		Matcher matcher = FIELD_PATTERN.matcher(expression);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		throw new RuleException("Not found field with expression " + expression);
	}

	private static RuleOperation parseRuleOperation(String rule) {
		Matcher matcher = OPERATION_PATTERN.matcher(rule);
		if (matcher.matches()) {
			String operation = operation(matcher.group(1));
			if (RuleOperation.isSupported(operation)) {
				return RuleOperation.valueOf(operation);
			} else {
				throw new UnsupportedOperationException(String.format("Operation in rule [%s] is not supported", operation));
			}
		}
		throw new RuleException(String.format("Format rule [%s] is not supported, please check format. Support format is 'operation $f{fieldName}'", rule));
	}

	private static String operation(String str) {
		return str.trim().replaceAll(SPACE, EMPTY).toUpperCase();
	}

}
