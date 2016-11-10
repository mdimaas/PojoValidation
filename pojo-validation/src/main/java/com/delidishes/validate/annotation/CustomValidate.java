package com.delidishes.validate.annotation;

import com.delidishes.validate.handler.IValidateHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomValidate {
	Class<? extends IValidateHandler<?>> handler();
	String message() default "This field isn't valid";
}
