package com.delidishes.validate.spring.test;

import com.delidishes.validate.ValidateException;
import com.delidishes.validate.spring.interceptor.PojoValidationInterceptor;
import com.delidishes.validate.spring.test.controller.TestControllerWithErrorHandler;
import com.delidishes.validate.spring.test.dto.TestPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContextForTest.xml")
public class ValidateInterceptorTest {

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@Autowired
	@Qualifier("validationInterceptor")
	private PojoValidationInterceptor interceptor;

	@Autowired
	private TestControllerWithErrorHandler testControllerWithErrorHandler;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void init() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	private void fillPostRequestBody(final MockHttpServletRequest request, String mappingUrl, String content) {
		byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		request.addHeader("Content-Length", contentBytes.length);
		request.addHeader("Connection", "keep-alive");
		request.setMethod(RequestMethod.POST.name());
		request.setRequestURI(mappingUrl);
		request.setContent(contentBytes);
	}

	@Test(expected = ValidateException.class)
	public void throwsValidateExceptionTest() throws Exception {
		TestPojo testPojo = new TestPojo();
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, testPojo);
		fillPostRequestBody(request, "/test", writer.toString());
		Object handler = handlerMapping.getHandler(request).getHandler();
		interceptor.preHandle(request, response, handler);
	}

	@Test
	public void successResponseFromControllerTest() throws Exception {
		TestPojo testPojo = new TestPojo();
		testPojo.setTest("test");
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, testPojo);
		fillPostRequestBody(request, "/test", writer.toString());
		Object handler = handlerMapping.getHandler(request).getHandler();
		interceptor.preHandle(request, response, handler);
	}

	@Test
	public void successFalseControllerWithErrorHandlerTest() throws Exception {
		TestPojo testPojo = new TestPojo();
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, testPojo);
		fillPostRequestBody(request, "/test2", writer.toString());
		Object handler = handlerMapping.getHandler(request).getHandler();
		try {
			interceptor.preHandle(request, response, handler);
		} catch (ValidateException e) {
			assertNotNull(testControllerWithErrorHandler.errorHandler(e));
		}
	}

	@Test
	public void successTrueControllerWithErrorHandlerTest() throws Exception {
		TestPojo testPojo = new TestPojo();
		testPojo.setTest("test2");
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, testPojo);
		fillPostRequestBody(request, "/test2", writer.toString());
		Object handler = handlerMapping.getHandler(request).getHandler();
		interceptor.preHandle(request, response, handler);
		assertEquals(testControllerWithErrorHandler.validateTest(testPojo), testPojo.getTest());
	}
}
