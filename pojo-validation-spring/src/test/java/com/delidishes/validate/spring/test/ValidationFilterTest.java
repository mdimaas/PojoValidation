package com.delidishes.validate.spring.test;

import com.delidishes.validate.spring.filter.ValidateFilter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

public class ValidationFilterTest {

	private MockFilterConfig mockFilterConfig;
	private MockHttpServletRequest mockHttpServletRequest;
	private MockHttpServletResponse mockHttpServletResponse;
	private MockFilterChain mockFilterChain;

	@Before
	public void setUp() throws Exception {
		mockFilterConfig = new MockFilterConfig();
		mockFilterChain = new MockFilterChain();
		mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletResponse = new MockHttpServletResponse();
	}

	@Test
	public void validateFilterTest() throws ServletException, IOException {
		ValidateFilter filter = new ValidateFilter();
		filter.init(mockFilterConfig);
		filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
		filter.destroy();
	}

	@Test
	public void validateFilterWithRequestContentTypeTest() throws ServletException, IOException {
		mockHttpServletRequest.setContentType(APPLICATION_JSON_UTF8_VALUE);
		ValidateFilter filter = new ValidateFilter();
		filter.init(mockFilterConfig);
		filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
		filter.destroy();
	}
}
