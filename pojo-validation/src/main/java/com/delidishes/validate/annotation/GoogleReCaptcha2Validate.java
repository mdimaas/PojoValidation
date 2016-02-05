package com.delidishes.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GoogleReCaptcha2Validate {

	String appSecret();
	String errorMessage() default "Captcha is not valid";
}
