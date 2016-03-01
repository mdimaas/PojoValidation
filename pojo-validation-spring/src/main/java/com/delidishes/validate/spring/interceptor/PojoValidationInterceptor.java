package com.delidishes.validate.spring.interceptor;

import com.delidishes.validate.exception.ValidateException;
import com.delidishes.validate.ValidateResult;
import com.delidishes.validate.spring.ValidType;
import com.delidishes.validate.spring.ValidateHttpServletWrapper;
import com.delidishes.validate.spring.ValidateSpringUtils;
import com.delidishes.validate.spring.annotation.Valid;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.delidishes.validate.ValidateUtils.*;
import static com.delidishes.validate.spring.ValidateSpringUtils.SUPPORT_CONTENT_TYPES;

public class PojoValidationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
		ValidateResult result = validationHandler(request, handler);
		if(!result.isValid()) {
			throw new ValidateException(result);
		}
		return super.preHandle(request, response, handler);
	}

	protected ValidateResult validationHandler(HttpServletRequest request, Object handler) throws IOException {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
		for (MethodParameter methodParameter : methodParameters) {
			Valid annotation = methodParameter.getParameterAnnotation(Valid.class);
			if (annotation != null) {
				return validateHandler(annotation.type(), readRequest(request, methodParameter.getParameterType()));
			}
		}

		return new ValidateResult(true);
	}

	private static  <T> T readRequest(HttpServletRequest request, Class<T> pojoClass) throws IOException {
		if(SUPPORT_CONTENT_TYPES.contains(request.getContentType())) {
			return ValidateSpringUtils.fromJson(new ValidateHttpServletWrapper(request).getBody(), pojoClass);
		}
		throw new UnsupportedOperationException(String.format("Not supported content type [%s]", request.getContentType()));
	}


	private static <T> ValidateResult validateHandler(ValidType[] types, T pojo) {
		List<ValidateResult> result = new ArrayList<>();
		for (ValidType type : types) {
			switch (type) {
				case CUSTOM:
					result.add(customHandlerValidate(pojo));
					break;
				case GOOGLE_RECAPTCHA2:
					result.add(googleReCaptcha2Validate(pojo));
					break;
				case NOT_NULL:
					result.add(emptyValidate(pojo));
					break;
				case RULES:
					result.add(rulesValidate(pojo));
					break;
				default:
					result.add(validate(pojo));
					break;
			}
		}
		return bitwiseUnion(result);
	}
}
