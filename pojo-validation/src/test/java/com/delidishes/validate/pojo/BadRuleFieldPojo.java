package com.delidishes.validate.pojo;

import com.delidishes.validate.annotation.Rule;
import com.delidishes.validate.annotation.Rules;

public class BadRuleFieldPojo {

    @Rules(rules = {@Rule(rule = "eq #f{ruleEqTest}")})
    public String ruleTest;
}
