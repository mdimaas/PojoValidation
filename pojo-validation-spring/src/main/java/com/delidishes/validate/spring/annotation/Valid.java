package com.delidishes.validate.spring.annotation;


import com.delidishes.validate.spring.ValidType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Valid {
	ValidType[] type() default {ValidType.FULL};
}
