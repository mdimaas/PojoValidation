package com.delidishes.validate;

import org.junit.Assert;
import org.junit.Test;

public class ValidateStringUtilsTest {

	@Test
	public void isEmptyTest(){
		Assert.assertTrue(ValidateStringUtils.isEmpty(null));
		Assert.assertTrue(ValidateStringUtils.isEmpty(ValidateStringUtils.EMPTY));
		Assert.assertTrue(ValidateStringUtils.isEmpty(ValidateStringUtils.SPACE));
	}

	@Test
	public void isNotEmptyTest(){
		Assert.assertTrue(ValidateStringUtils.isNotEmpty("test"));
		Assert.assertFalse(ValidateStringUtils.isNotEmpty(ValidateStringUtils.EMPTY));
	}
}
