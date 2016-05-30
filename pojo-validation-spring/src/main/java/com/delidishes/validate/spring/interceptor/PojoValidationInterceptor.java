package com.delidishes.validate.spring.interceptor;

import com.delidishes.validate.ValidateResult;
import com.delidishes.validate.exception.ValidateException;
import com.delidishes.validate.spring.ValidType;
import com.delidishes.validate.spring.ValidateHttpServletWrapper;
import com.delidishes.validate.spring.annotation.Valid;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.delidishes.validate.ValidateUtils.*;

public class PojoValidationInterceptor extends HandlerInterceptorAdapter {

	private RequestMappingHandlerAdapter handlerAdapter;

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

	@SuppressWarnings("unchecked")
	private <T> T readRequest(HttpServletRequest request, Class<T> pojoClass) throws IOException {
		for (HttpMessageConverter<?> messageConverter : handlerAdapter.getMessageConverters()) {
			if (messageConverter.canRead(pojoClass, MediaType.parseMediaType(request.getContentType()))) {
				return (T) messageConverter.read((Class) pojoClass, new ValidateHttpServletWrapper(request));
			}
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

	public RequestMappingHandlerAdapter getHandlerAdapter() {
		return handlerAdapter;
	}

	public void setHandlerAdapter(RequestMappingHandlerAdapter handlerAdapter) {
		this.handlerAdapter = handlerAdapter;
	}
}
