package com.delidishes.validate.pojo;

import com.delidishes.validate.annotation.Rule;
import com.delidishes.validate.annotation.Rules;

public class BadRule {

    @Rules(rules = {@Rule(rule = "test my field")})
    public String ruleTest;
}
