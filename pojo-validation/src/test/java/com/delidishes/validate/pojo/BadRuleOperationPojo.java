package com.delidishes.validate.pojo;

import com.delidishes.validate.annotation.Rule;
import com.delidishes.validate.annotation.Rules;

public class BadRuleOperationPojo {

        @Rules(rules = {@Rule(rule = "gt #f{ruleEqTest}")})
        public String ruleTest;
}
