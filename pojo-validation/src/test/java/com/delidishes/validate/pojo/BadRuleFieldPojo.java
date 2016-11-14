package com.delidishes.validate.pojo;

import com.delidishes.validate.annotation.Rule;
import com.delidishes.validate.annotation.Rules;
import com.delidishes.validate.handler.pojo.RuleOperation;

public class BadRuleFieldPojo {

    @Rules(rules = {@Rule(rule = RuleOperation.EQ, field = "ruleEqTest")})
    public String ruleTest;
}
