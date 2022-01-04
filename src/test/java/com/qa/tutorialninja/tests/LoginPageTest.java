package com.qa.tutorialninja.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.tutorialsninja.base.BaseTest;
import com.qa.tutorialsninja.testlisteners.ExtentReportListener;
import com.qa.tutorialsninja.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

// if we wish to a apply a Listener on entire suite we must add Listener in our test runner (xml file) but if we want to 
// specifically apply it on a test class directly, we should declare it on top of the test class 

//@Listeners(ExtentReportListener.class)
public class LoginPageTest extends BaseTest {
	@Description("verifying login page title")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=1)
	public void getLoginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		System.out.println("login page title is : " + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("verifying login page header value")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void getHeaderValueTest() {
		Assert.assertEquals(loginPage.getHeaderValue(), Constants.LOGIN_PAGE_HEADER);
	}
	
	@Description("verifying if forgotten password link exist")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3)
	public void isForgotPasswordExistTest() {
		Assert.assertTrue(loginPage.isForgotPasswordExist());
	}
	
	@Description("login test with user name and password")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=4)
	public void doLoginTest() {
		loginPage.doLogin(prop.getProperty("emailId"), prop.getProperty("password"));
	}
}

