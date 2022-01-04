package com.qa.tutorialninja.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.tutorialsninja.base.BaseTest;
import com.qa.tutorialsninja.utils.Constants;
import com.qa.tutorialsninja.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void registerPageSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegisterData() {
		Object data[][] = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return data;
	}
	
	@Test(dataProvider = "getRegisterData")
	public void accountRegisterTest(String fName, String lName, String email, String phone, String pWord, String subsciption) {
		registerPage.accountRegister(fName, lName, email, phone, pWord, subsciption);
	}
}
