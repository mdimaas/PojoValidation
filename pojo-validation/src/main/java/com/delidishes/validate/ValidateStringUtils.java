package com.delidishes.validate;

public final class ValidateStringUtils {

	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String LF = "\n";
	public static final String CR = "\r";
	public static final String NEW_LINE = LF + CR;

	private ValidateStringUtils(){}

	public static boolean isEmpty(String s) {
		return s == null || EMPTY.equals(s.trim());
	}

	public static boolean isNotEmpty(String s){
		return !isEmpty(s);
	}
}

