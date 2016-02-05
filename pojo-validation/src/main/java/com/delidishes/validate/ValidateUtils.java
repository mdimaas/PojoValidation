package com.delidishes.validate;

import com.delidishes.validate.annotation.CustomValidate;
import com.delidishes.validate.annotation.GoogleReCaptcha2Validate;
import com.delidishes.validate.annotation.NotNull;
import com.delidishes.validate.annotation.Rules;
import com.delidishes.validate.handler.GoogleReCaptcha2ValidateHandler;
import com.delidishes.validate.handler.IValidateHandler;
import com.delidishes.validate.handler.RuleValidateHandler;
import com.delidishes.validate.handler.builder.RuleBuilder;
import com.delidishes.validate.handler.pojo.GoogleReCaptcha2;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.delidishes.validate.ValidateStringUtils.isNotEmpty;

public final class ValidateUtils {

	public static final Logger log = Logger.getLogger(ValidateUtils.class);

	private ValidateUtils() {
	}

	public static ValidateResult bitwiseUnion(Collection<ValidateResult> result) {
		ValidateResult validateResult = new ValidateResult(true);
		result.stream().filter(r -> !r.isValid() && r.getErrors() != null && r.getErrors().size() > 0).forEach(r -> {
			validateResult.setValid(false);
			validateResult.getErrors().addAll(r.getErrors());
		});

		return validateResult;
	}

	public static <T> ValidateResult validate(T pojo) {
		return bitwiseUnion(Arrays.asList(
				emptyValidate(pojo),
				googleReCaptcha2Validate(pojo),
				customHandlerValidate(pojo),
				rulesValidate(pojo)
		));
	}

	public static <T> ValidateResult emptyValidate(T pojo) {
		ValidateResult result = new ValidateResult(false);
		fieldsByAnnotation(pojo, NotNull.class).forEach(f -> {
			try {
				f.setAccessible(true);
				Object value = f.get(pojo);
				boolean verify = value != null && value instanceof String ? isNotEmpty(String.valueOf(value)) : Objects.nonNull(value);
				writeResult(result, verify, f.getAnnotation(NotNull.class).errorMessage());
			} catch (IllegalAccessException e) {
				log.error("Validate error in method {Validate.emptyValidate}", e);
			}
		});
		return result;
	}

	public static <T> ValidateResult googleReCaptcha2Validate(T pojo) {
		ValidateResult result = new ValidateResult(false);
		fieldsByAnnotation(pojo, GoogleReCaptcha2Validate.class).forEach(field -> {
			try {
				field.setAccessible(true);
				GoogleReCaptcha2 googleReCaptcha2 = new GoogleReCaptcha2();
				GoogleReCaptcha2Validate annotation = field.getAnnotation(GoogleReCaptcha2Validate.class);
				googleReCaptcha2.setAppSecret(annotation.appSecret());
				googleReCaptcha2.setErrorMessage(annotation.errorMessage());
				googleReCaptcha2.setValue(String.valueOf(field.get(pojo)));
				boolean verify = new GoogleReCaptcha2ValidateHandler().verify(googleReCaptcha2);
				writeResult(result, verify, annotation.errorMessage());
			} catch (IllegalAccessException e) {
				log.error("Validate error in method {Validate.googleReCaptcha2Validate}", e);
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> ValidateResult customHandlerValidate(T pojo) {
		ValidateResult validateResult = new ValidateResult(false);
		fieldsByAnnotation(pojo, CustomValidate.class).forEach(field -> {
			field.setAccessible(true);
			CustomValidate annotation = field.getAnnotation(CustomValidate.class);
			try {
				IValidateHandler validateHandler = (IValidateHandler) annotation.handler().newInstance();
				boolean verify = validateHandler.verify(field.get(pojo));
				writeResult(validateResult, verify, annotation.errorMessage());
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("Validate error in method {Validate.customHandlerValidate}", e);
			}
		});

		return validateResult;
	}

	private static <T> List<Field> fieldsByAnnotation(T dto, Class<? extends Annotation> annotation) {
		return Arrays.asList(dto.getClass().getDeclaredFields()).stream()
				.filter(f -> f.getAnnotation(annotation) != null)
				.collect(Collectors.toList());
	}

	private static void writeResult(final ValidateResult result, boolean value, String error) {
		result.setValid(value);
		if (!value) {
			result.addError(error);
		}
	}


	public static <T> ValidateResult rulesValidate(T pojo) {
		ValidateResult validateResult = new ValidateResult(false);
		fieldsByAnnotation(pojo, Rules.class).forEach(field -> {
			field.setAccessible(true);
			Arrays.stream(field.getAnnotation(Rules.class).rules()).forEach(rule -> {
				try {
					boolean result = new RuleValidateHandler<>().verify(RuleBuilder.createRule(rule.rule(), field.get(pojo), pojo));
					log.debug(String.format("VALIDATION: Field = [%s]. Rule [%s] is %s", field.getName(), rule.rule(), result));
					writeResult(validateResult, result, rule.errorMessage());
				} catch (NoSuchFieldException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});

		});
		return validateResult;
	}
}
