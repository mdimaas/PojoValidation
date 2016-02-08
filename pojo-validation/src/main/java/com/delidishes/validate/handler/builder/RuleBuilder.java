package com.delidishes.validate.handler.builder;

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

	public static <T, P> Rule<T> createRule(String ruleStr, T currentValue, P pojo) throws NoSuchFieldException, IllegalAccessException {
		Rule<T> rule = new Rule<>();
		rule.setOperation(parseRuleOperation(ruleStr));
		rule.setCurrentValue(currentValue);
		rule.setCompareValue(parseRuleCompareValue(ruleStr, pojo));
		return rule;
	}

	private static <T, P> T parseRuleCompareValue(String ruleStr, P pojo) throws NoSuchFieldException, IllegalAccessException {
		Field field = pojo.getClass().getField(parseFieldName(ruleStr));
		field.setAccessible(true);
		return (T) field.get(pojo);
	}

	private static String parseFieldName(String expression) {
		Matcher matcher = FIELD_PATTERN.matcher(expression);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		throw new IllegalArgumentException("Not found field with expression " + expression);
	}

	private static RuleOperation parseRuleOperation(String rule) {
		Matcher matcher = OPERATION_PATTERN.matcher(rule);
		if (matcher.matches()) {
			return RuleOperation.valueOf(matcher.group(1).trim().replaceAll(SPACE, EMPTY).toUpperCase());
		}

		throw new UnsupportedOperationException(String.format("Operation in rule [%s] is not supported", rule));
	}

}
