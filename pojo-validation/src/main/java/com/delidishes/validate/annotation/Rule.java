package com.delidishes.validate.annotation;

import com.delidishes.validate.handler.pojo.RuleOperation;

public @interface Rule {
	RuleOperation rule();
	String val() default "";
	String field() default "";
	String message() default "This rule is not valid";
}
