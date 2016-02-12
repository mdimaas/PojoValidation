package com.delidishes.validate.spring.interceptor;

import com.delidishes.validate.ValidateException;
import com.delidishes.validate.ValidateResult;
import com.delidishes.validate.spring.ValidType;
import com.delidishes.validate.spring.ValidateHttpServletWrapper;
import com.delidishes.validate.spring.ValidateSpringUtils;
import com.delidishes.validate.spring.annotation.Valid;
import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.delidishes.validate.ValidateUtils.*;
import static com.delidishes.validate.spring.ValidateSpringUtils.SUPPORT_CONTENT_TYPES;

public class PojoValidationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
		ValidateResult result = validationHandler(request, response, handler);
		if(!result.isValid()) {
			throw new ValidateException(result);
		}
		return super.preHandle(request, response, handler);
	}

	public ValidateResult validationHandler(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
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

	private <T> T readRequest(HttpServletRequest request, Class<T> pojoClass) throws IOException {
		if(SUPPORT_CONTENT_TYPES.contains(request.getContentType())) {
			return ValidateSpringUtils.fromJson(new ValidateHttpServletWrapper(request).getBody(), pojoClass);
		}
		throw new UnsupportedOperationException(String.format("Not supported content type [%s]", request.getContentType()));
	}


	private <T> ValidateResult validateHandler(ValidType[] types, T pojo) {
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