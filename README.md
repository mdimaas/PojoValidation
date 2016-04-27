# PojoValidation

[![Build Status](https://api.travis-ci.org/mdimaas/PojoValidation.svg)](https://travis-ci.org/mdimaas/PojoValidation)
[![Coverage Status](https://coveralls.io/repos/github/mdimaas/PojoValidation/badge.svg?branch=master)](https://coveralls.io/github/mdimaas/PojoValidation?branch=master)

Java library for validation POJO

1. Empty fields validation
2. Google ReCaptcha2 validation
3. Rule validation
4. Custom validation

## Empty fields validation

For validation POJO's fields by empty you need use annotation *@NotNull*

###### Example:
```java

public class Pojo {
	@NotNull
	private String field;
	...
}

```

```java

Pojo pojo = new Pojo();
...
ValidateResult result = ValidateUtils.emptyValidate(pojo);

```

## Google ReCaptcha2 validation

For validation GoogleReCaptcha in server side you need use annotation *@GoogleReCaptcha2Validate*

###### Example:

```java
public class Pojo {
	@GoogleReCaptcha2Validate(appSecret = "you secret key")
	private String captchaTest;
	...
}
```

```java

Pojo pojo = new Pojo();
...
ValidateResult result = ValidateUtils.googleReCaptcha2Validate(pojo);

```

## Rule validation

For validation by rule you need use annotation *@Rules*

###### Example:

```java

public class Pojo {
	private String field1;
	@Rules(rules = {@Rule(rule = "eq #f{field1}")})
	private String field2;
	...
}
```

```java

Pojo pojo = new Pojo();
...
ValidateResult result = ValidateUtils.rulesValidate(pojo);

```

## Custom validation

For custom validation you need use annotation *@CustomValidate*

###### Example:

Created custom handler for validation:
```java

public class CustomHandlerValidate implements IValidateHandler<Integer> {
	@Override
	public boolean verify(Integer value) {
		return value * 2 < 100;
	}
}

```

Validation: 
```java

public class Pojo {
	@CustomValidate(handler = TestCustomHandlerValidate.class)
    private Integer field;
    ...
}
```

```java

Pojo pojo = new Pojo();
...
ValidateResult result = ValidateUtils.customHandlerValidate(pojo);

```

## Other

If you want throws exception after bad validation you need use this: 

```java

ValidateUtils.validate(pojo).throwsExceptionIfFalse();

```