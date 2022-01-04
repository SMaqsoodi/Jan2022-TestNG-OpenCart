package com.qa.tutorialninja.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.tutorialsninja.base.BaseTest;
import com.qa.tutorialsninja.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class AccountPageTest extends BaseTest {

	@BeforeClass
	public void accountPageSetup() {
		accountPage = loginPage.doLogin(prop.getProperty("emailId"), prop.getProperty("password"));
	}
	
	@Description("verifying account page title")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=1)
	public void getAccPageTitleTest() {
		String accountPageTitle = accountPage.getAccPageTitle();
		System.out.println("account page title is : " + accountPageTitle);
		Assert.assertEquals(accountPageTitle, Constants.ACCOUNT_PAGE_TITLE);
	}
	
	@Description("verifying account page header value")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void getHeaderValueTest() {
		String accountPageHeaderTitle = accountPage.getHeaderValue();
		System.out.println("logo title is : " + accountPageHeaderTitle);
		Assert.assertEquals(accountPageHeaderTitle, Constants.ACCOUNT_PAGE_HEADER);
	
	}
	
	@Description("verifying account page section counts")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=3)
	public void getAccSectionCountTest() {
		Assert.assertEquals(accountPage.getAccSectionCount(), Constants.ACCOUNT_PAGE_HEADERS_COUNT); // or
		//Assert.assertTrue(accountPage.getAccSectionCount() == Constants.ACCOUNT_PAGE_HEADERS_COUNT); 
	}
	
	@Description("verifying account page section list")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=4)
	public void getAccSectionListTest() {
				
		Assert.assertEquals(accountPage.getAccSectionList(), Constants.getAccountSectionList());
	}
	
	@Description("verifying account page search function")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=5)
	public void doSearchTest() {
		Assert.assertTrue(accountPage.doSearch("iMac"));
	}
	
}
