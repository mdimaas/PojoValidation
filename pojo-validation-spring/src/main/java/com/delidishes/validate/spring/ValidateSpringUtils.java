package com.delidishes.validate.spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public final class ValidateSpringUtils {

	public static final List<String> SUPPORT_CONTENT_TYPES = unmodifiableList(Arrays.asList("application/json;charset=UTF-8", "application/json"));

	public static <T> T fromJson(String json, Class<T> pojoClass) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(json, pojoClass);
	}
}
