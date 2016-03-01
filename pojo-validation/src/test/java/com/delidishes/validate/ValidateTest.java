package com.delidishes.validate;

import com.delidishes.validate.annotation.Rule;
import com.delidishes.validate.exception.FieldAccessException;
import com.delidishes.validate.exception.RuleException;
import com.delidishes.validate.exception.ValidateException;
import com.delidishes.validate.pojo.BadRule;
import com.delidishes.validate.pojo.BadRuleFieldPojo;
import com.delidishes.validate.pojo.BadRuleOperationPojo;
import com.delidishes.validate.pojo.TestPojo;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateTest {

	private TestPojo testPojo;

	@Before
	public void init() {
		testPojo = new TestPojo();
	}

	@Test
	public void goolgeReCaptcha2IfFalse(){
		assertFalse(ValidateUtils.googleReCaptcha2Validate(testPojo).isValid());
	}

	@Test
	public void rulesPojoValidateRuleEqAndNotEqTrueTest(){
		testPojo.ruleEqTest1 = "test";
		testPojo.ruleEqTest2 = "test";
		testPojo.ruleNotEqTest1 = "test1";
		testPojo.ruleNotEqTest2 = "test2";
		assertTrue(ValidateUtils.rulesValidate(testPojo).isValid());
	}

	@Test
	public void rulesPojoValidateRuleEqFalseTest(){
		testPojo.ruleEqTest1 = "test1";
		testPojo.ruleEqTest2 = "test2";
		assertFalse(ValidateUtils.rulesValidate(testPojo).isValid());
	}

	@Test
	public void rulesPojoValidateRuleNotEqTrueTest(){
		testPojo.ruleNotEqTest1 = "test1";
		testPojo.ruleNotEqTest2 = "test2";
		assertTrue(ValidateUtils.rulesValidate(testPojo).isValid());
	}

	@Test
	public void rulesPojoValidateRuleNotEqFalseTest(){
		testPojo.ruleEqTest1 = "test";
		testPojo.ruleEqTest2 = "test";
		testPojo.ruleNotEqTest1 = "test1";
		testPojo.ruleNotEqTest2 = "test1";
		assertFalse(ValidateUtils.rulesValidate(testPojo).isValid());
	}

	@Test
	public void rulesPojoValidateCustomHandlerTrueTest() throws Exception {
		testPojo.customHandler = 20;
		assertTrue(ValidateUtils.customHandlerValidate(testPojo).isValid());
	}

	@Test
	public void rulesPojoValidateCustomHandlerFalseTest() throws Exception {
		testPojo.customHandler = 100;
		assertFalse(ValidateUtils.customHandlerValidate(testPojo).isValid());
	}

	@Test(expected = ValidateException.class)
	public void throwsValidateExceptionTest() throws Exception {
		testPojo.customHandler = 100;
		ValidateUtils.customHandlerValidate(testPojo).throwsExceptionIfFalse();
	}

	@Test
	public void fullValidateFalseTest() throws Exception {
		testPojo.ruleEqTest1 = "test";
		testPojo.ruleEqTest2 = "test";
		testPojo.ruleNotEqTest1 = "test1";
		testPojo.ruleNotEqTest2 = "test2";
		testPojo.customHandler = 20;
		assertFalse(ValidateUtils.validate(testPojo).isValid());
	}

	@Test
	public void unionTrueTest() throws Exception {
		testPojo.ruleNotEqTest1 = "test1";
		testPojo.ruleNotEqTest2 = "test2";
		testPojo.customHandler = 20;
		assertTrue(ValidateUtils.bitwiseUnion(Arrays.asList(ValidateUtils.rulesValidate(testPojo), ValidateUtils.customHandlerValidate(testPojo))).isValid());
	}

	@Test
	public void unionFalseTest(){
		testPojo.ruleNotEqTest1 = "test1";
		testPojo.ruleNotEqTest2 = "test2";
		assertFalse(ValidateUtils.bitwiseUnion(Arrays.asList(ValidateUtils.rulesValidate(testPojo), ValidateUtils.googleReCaptcha2Validate(testPojo))).isValid());
	}

	@Test
	public void notNullValidateTrueTest() throws Exception {
		testPojo.strTestValue = "test";
		assertTrue(ValidateUtils.emptyValidate(testPojo).isValid());
	}

	@Test
	public void notNullValidateFalseTest() throws Exception {
		assertFalse(ValidateUtils.emptyValidate(testPojo).isValid());
	}

	@Test(expected = ValidateException.class)
	public void throwsValidationExceptionTest2() throws Exception {
		testPojo.customHandler = 100;
		ValidateUtils.customHandlerValidate(testPojo).throwsExceptionIfFalse(error -> "Override error text test!");
	}

	@Test(expected = ValidateException.class)
	public void throwsValidationExceptionTest3() throws Exception {
		testPojo.customHandler = 100;
		if(!ValidateUtils.customHandlerValidate(testPojo).isValid()){
			throw new ValidateException();
		}
	}

	@Test
	public void eptyThrowsValidationExceptionTest1() throws Exception {
		testPojo.customHandler = 20;
		ValidateUtils.customHandlerValidate(testPojo).throwsExceptionIfFalse(error -> "Override error text test!");
	}

	@Test
	public void emptyValidationExceptionTest2() throws Exception {
		testPojo.customHandler = 20;
		ValidateUtils.customHandlerValidate(testPojo).throwsExceptionIfFalse();
	}

	@Test(expected = FieldAccessException.class)
	public void badRuleFieldValidationTest() {
		ValidateUtils.rulesValidate(new BadRuleFieldPojo());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void badRuleOperationValidationTest() {
		ValidateUtils.rulesValidate(new BadRuleOperationPojo());
	}

	@Test(expected = RuleException.class)
	public void badRuleValidationTest() {
		ValidateUtils.rulesValidate(new BadRule());
	}
}
