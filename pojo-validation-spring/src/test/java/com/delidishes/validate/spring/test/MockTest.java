package com.delidishes.validate.spring.test;

import com.delidishes.validate.spring.filter.ValidateFilter;
import com.delidishes.validate.spring.interceptor.PojoValidationInterceptor;
import com.delidishes.validate.spring.test.controller.TestControllerWithErrorHandler;
import com.delidishes.validate.spring.test.dto.TestPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.StringWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContextForTest.xml")
public class MockTest {

	@Autowired
	private TestControllerWithErrorHandler testController;

	@Autowired
	private PojoValidationInterceptor interceptor;

	@Test
	public void initValidateFilterWithValidateWrapperTest() throws Exception {
		TestPojo testPojo = new TestPojo();
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, testPojo);

		ValidateFilter validateFilter = new ValidateFilter();
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testController)
				.addFilter(validateFilter, "/**")
				.addInterceptors(interceptor)
				.build();
		mockMvc.perform(
				MockMvcRequestBuilders.post("/test")
						.contentType(MediaType.APPLICATION_JSON)
						.content(writer.toString().getBytes())
		).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}


}
