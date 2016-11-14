package com.delidishes.validate.pojo;

import com.delidishes.validate.annotation.Rule;
import com.delidishes.validate.annotation.Rules;
import com.delidishes.validate.handler.pojo.RuleOperation;

public class BadRule {

    @Rules(rules = {@Rule(rule = RuleOperation.NOTEQ)})
    public String ruleTest;
}
