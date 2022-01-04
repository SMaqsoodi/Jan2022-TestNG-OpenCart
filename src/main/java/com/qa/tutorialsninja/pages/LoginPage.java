package com.qa.tutorialsninja.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.tutorialsninja.base.BasePage;
import com.qa.tutorialsninja.utils.Constants;
import com.qa.tutorialsninja.utils.ElementUtil;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("Epic 100: define login page features ... ")
@Story("US 101: define the login page class feature with title, forgot password link and login functionality")
public class LoginPage extends BasePage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;	
	// By locators
	private By emailId = By.id("input-email");
	private By passWord = By.id("input-password");
	private By loginButton = By.xpath("//input[@value ='Login' and @type='submit']");
	private By loginPageHeader = By.cssSelector("#logo a");
	private By forgotPassword = By.linkText("Forgotten Password");
	
	private By registerLink = By.linkText("Register");
	
	// constructor of the page
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	// page actions
	@Step("getting login page title ... ")
	public String getLoginPageTitle() {
		return elementUtil.waitForPageTitle(Constants.LOGIN_PAGE_TITLE, 10);
	}	
			
	@Step("getting login page header value ... ")
	public String getHeaderValue() {
		String header = elementUtil.getElementText(loginPageHeader);
		System.out.println("login page headre is : " + header);
		
		return header;
	}
	
	@Step("checking if forgot password link is displayed ... ")
	public boolean isForgotPasswordExist() {
		//return driver.findElement(forgotPassword).isDisplayed(); // or using ElementUtil class
		return elementUtil.isDisplayed(forgotPassword);
	}
	
	@Step("logging with user name : {0} and password : {1} ...")
	public AccountPage doLogin(String un, String pwd) {
		System.out.println("Loing with : " + un + " and " + pwd);

		elementUtil.doSendKeys(emailId, un);
		elementUtil.doSendKeys(passWord, pwd);
		elementUtil.doClick(loginButton);
		
		return new AccountPage(driver);
	}
	
	@Step("navigating to register page ... ")
	public RegisterPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		
		return new RegisterPage(driver);
	}

}

