package com.delidishes.validate;

import com.delidishes.validate.handler.GoogleReCaptcha2ValidateHandler;
import com.delidishes.validate.handler.pojo.GoogleReCaptcha2;
import com.delidishes.validate.pojo.TestPojo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ValidateTest {

	private TestPojo testPojo;

	@Before
	public void init() {
		testPojo = new TestPojo();
	}

	@Test
	public void recaptcha2ValidateFalseTest() {
		GoogleReCaptcha2 googleReCaptcha2 = new GoogleReCaptcha2();
		googleReCaptcha2.setAppSecret("7894I_QSPPPPPPPPYLwvXll5ku7EzR6wzmdkwdocLA");
		googleReCaptcha2.setValue("03AHJ_VutMjdxLtxKLdpAZSohiWGFOVIJ9w7uMoAoIYa6qwkXgSL09w3A7pP4QJvjGc8PzN1NipnnYmjcRq68Fv9qoUhi2wM8W-q8lx-mG-vjX_Jcd_Btw1JIYQLR0dvEJ5M1DIi8XmqBMDQ1usnu8IG2xlzIgVr1clfirbdDLdWwDJDC99dtIo_onSxOh1ubQMBoYVzHL9a9p51KGQV_hUoLvZd3vHm_HuUiuP0aGbjW-X32hzeWtc6fkfwv8Eo9snBV4FDYnrcyuYxGMY_83WGpqtPBtC-UAwu53i7wF6i4LoTO35E8en6vBUiJ-YfECeZbujwGLvRprDeEy_RYJAoXjL0UcefAFoS0qzUNNpkkXtHs_TpqMpqOdvrJ7qWGOFHok0p4AruOj0MOl4bPNIPSAnvOn73RbI30_IOkTlsPY4Ax6dKpUOwx2d2KNmmNG94ra8282RnHvMb30lGBsmS4w7LZdn9WQYIoxFvDd1jahkkFMjkCHfBUvb_vSDMNRGkoRQhUUDN17F8lgNPVbh6shfSvzzrXpJltL1aPvdckv9d8JsMRhiy0sURcdZb7QVTYOB2T9bml8Z7DmAaANHy-CoAzeT5_zVhyUeiydQFqKFkqoktqqwQBOwK2hFB-T9tQIaWEQxV0cMLBuPGTyPbYFn0vG4HVkqRCcTDmx5YycUDV2V-BH0lCTLRFUGB-cXwmp3exPiyIBKvtBE4GNhD3vLYz9AA6KB8V0zCUH2SHECgSqoEPAKOIiEFqQCyEHxqPwU5OZLQ8foLhiyoIjHVkGm92CcQENRHVeuPU7zvmCqEN_kVU1K3fk11SZm912GeGvwuDRR_lsWhscjvseFdFesg1_2p_lAC_wF5v1WPiUiBJCclLJG1Xw9XY1GqHkR9FaTdqdBxf81tYWEkVOHDXEZxhx1_87cHnCJc9FLUzd8JzNlW1h5uE");
		assertFalse(new GoogleReCaptcha2ValidateHandler().verify(googleReCaptcha2));
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
	public void rulesPojoValidateCustomHandlerTrueTest(){
		testPojo.customHandler = 20;
		assertTrue(ValidateUtils.customHandlerValidate(testPojo).isValid());
	}

	@Test
	public void rulesPojoValidateCustomHandlerFalseTest(){
		testPojo.customHandler = 100;
		assertFalse(ValidateUtils.customHandlerValidate(testPojo).isValid());
	}
}
