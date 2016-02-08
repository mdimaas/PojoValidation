package com.delidishes.validate.pojo;

import com.delidishes.validate.annotation.*;
import com.delidishes.validate.handler.TestCustomHandlerValidate;

public class TestPojo {
	@NotNull
	public String strTestValue;
	@GoogleReCaptcha2Validate(appSecret = "test")
	public String captchaTest;
	public String ruleEqTest1;
	@Rules(rules = {@Rule(rule = "eq #f{ruleEqTest1}")})
	public String ruleEqTest2;

	@Rules(rules = {@Rule(rule = "not eq #f{ruleNotEqTest2}")})
	public String ruleNotEqTest1;
	@Rules(rules = {@Rule(rule = "not eq #f{ruleNotEqTest1}")})
	public String ruleNotEqTest2;

	@CustomValidate(handler = TestCustomHandlerValidate.class)
	public Integer customHandler;

}
