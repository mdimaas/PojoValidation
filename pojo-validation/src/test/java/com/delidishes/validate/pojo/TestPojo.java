package com.delidishes.validate.pojo;

import com.delidishes.validate.annotation.*;
import com.delidishes.validate.handler.TestCustomHandlerValidate;
import com.delidishes.validate.handler.pojo.RuleOperation;

import javax.validation.constraints.NotNull;

public class TestPojo {
	@NotNull
	public String strTestValue;
	@GoogleReCaptcha2Validate(appSecret = "test")
	public String captchaTest;
	public String ruleEqTest1;
	@Rules(rules = {@Rule(rule = RuleOperation.EQ, field = "ruleEqTest1", message = "ruleEqTest2 not eq ruleEqTest1")})
	public String ruleEqTest2;

	@Rules(rules = {@Rule(rule = RuleOperation.NOTEQ, field = "ruleNotEqTest2", message = "ruleNotEqTest1 eq ruleNotEqTest2")})
	public String ruleNotEqTest1;
	@Rules(rules = {@Rule(rule = RuleOperation.NOTEQ, val = "[]", message = "ruleNotEqTest2 eq []")})
	public String ruleNotEqTest2;

	@CustomValidate(handler = TestCustomHandlerValidate.class)
	public Integer customHandler;

}
