package com.qa.tutorialsninja.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.tutorialsninja.base.BasePage;
import com.qa.tutorialsninja.utils.Constants;
import com.qa.tutorialsninja.utils.ElementUtil;

public class RegisterPage extends BasePage {
	private WebDriver driver; // we can skip declaring web driver here, because we have already declared in BasePage 
	ElementUtil elementUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailId = By.id("input-email");
	private By telePhone = By.id("input-telephone");
	private By passWord = By.id("input-password");
	private By confirmPassWord = By.id("input-confirm");
	
//	private By subScriptionYes = By.xpath("//input[@type='radio' and @name='newsletter' and @value='1']");
//	private By subScriptionNo = By.xpath("//input[@type='radio' and @name='newsletter' and @value='0']");
	private By subScriptionYes = By.xpath("//label[@class='radio-inline'][position()=1]");
	private By subScriptionNo = By.xpath("//label[@class='radio-inline'][position()=2]");
	
	private By agreeCheckBox = By.name("agree");
	private By contiuneButton = By.xpath("//input[@type='submit' and @value='Continue']");
	
	private By accountSuccessMessg = By.cssSelector("#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	//constructor
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}
	
	public boolean accountRegister(String fName, String lName, String email, String phone, 
								String pWord, String subScribe) {
		
		elementUtil.doSendKeys(firstName, fName);
		elementUtil.doSendKeys(lastName, lName);
		elementUtil.doSendKeys(emailId, email);
		elementUtil.doSendKeys(telePhone, phone);
		elementUtil.doSendKeys(passWord, pWord);
		elementUtil.doSendKeys(confirmPassWord, pWord);
		
		if(subScribe.equalsIgnoreCase("Yes")) {
			elementUtil.doClick(subScriptionYes);
		}
		else if(subScribe.equalsIgnoreCase("no")) {
			elementUtil.doClick(subScriptionNo);
		}
		else {
			System.out.println("please pass only Yes or No for subscription ");
		}
		
		elementUtil.doClick(agreeCheckBox);
		elementUtil.doClick(contiuneButton);
		
		String text = elementUtil.getElementText(accountSuccessMessg);
		if(text.contains(Constants.ACCOUTN_SUCCESS_MESSG)) {
			elementUtil.doClick(logoutLink);
			elementUtil.doClick(registerLink);
			return true;
		}
		return false;
	}
	
}
