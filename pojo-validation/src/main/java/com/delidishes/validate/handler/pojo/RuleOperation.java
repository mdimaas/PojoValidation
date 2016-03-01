package com.delidishes.validate.handler.pojo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum RuleOperation {
	EQ, NOTEQ;

	private static final List<String> SUPPORTED_OPERATION = Collections.unmodifiableList(Arrays.asList(EQ.toString(), NOTEQ.toString()));

	public static boolean isSupported(String operation) {
		return SUPPORTED_OPERATION.contains(operation);
	}
}
