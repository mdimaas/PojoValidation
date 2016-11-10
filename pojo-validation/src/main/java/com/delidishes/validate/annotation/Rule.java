package com.delidishes.validate.annotation;

public @interface Rule {
	/**
	 * (Required) Validate expression
	 * E.g. exist class
	 * <pre>
	 *     {@code
	 *			class User{
	 *			 String name;
	 *			 String pwd;
	 *			 String confirmPwd;
	 *			}
	 *     }
	 * </pre>
	 * We need validate two field in class, we add rule @Rule(rule = "eq $f{confirmPwd}")
	 * @return
	 */
	String rule();
	String message() default "This rule is not valid";
}
