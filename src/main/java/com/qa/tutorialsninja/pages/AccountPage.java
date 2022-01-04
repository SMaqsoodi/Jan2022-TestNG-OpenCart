package com.qa.tutorialsninja.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.tutorialsninja.base.BasePage;
import com.qa.tutorialsninja.utils.Constants;
import com.qa.tutorialsninja.utils.ElementUtil;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("Epic 100: design account page ... ")
@Story("US 101: designing account page with title, header, account page sections, search product and selecting product from the search result")
public class AccountPage extends BasePage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	// By locators
	private By accPageHeader = By.cssSelector("div#logo a");
	private By accSectionHeaders = By.cssSelector("div#content h2");
	private By searchText = By.cssSelector("div#search input[name='search']");
	private By searchButton = By.cssSelector("div#search button[type='button']");
	private By accSectionLinks = By.cssSelector("div#content ul li a");
	private By searchResult = By.cssSelector(".product-layout .product-thumb");
	private By resultItems = By.cssSelector(".product-thumb h4 a");
	
	// Page constructor
	public AccountPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
		elementUtil.getElement(searchText).clear();
	}
	
	// page actions
	@Step("getting account page title ... ")
	public String getAccPageTitle() {
		return elementUtil.waitForPageTitle(Constants.ACCOUNT_PAGE_TITLE, 10);
	}	
	
	@Step("checking header value ... ")
	public String getHeaderValue() {
		if(elementUtil.isDisplayed(accPageHeader)) {
			System.out.println("Account page headre is : " + elementUtil.getElementText(accPageHeader));
			return elementUtil.getElementText(accPageHeader);
		}
		return null;
	}
		
	@Step("verifying account page section counts ... ")
	public int getAccSectionCount() {
		return elementUtil.getElements(accSectionHeaders).size();
	}
	
	@Step("verifying account page section list ... ")
	public List<String> getAccSectionList() {
		List<String> accList = new ArrayList<>();
		List<WebElement> accSectionList = elementUtil.getElements(accSectionHeaders);
		for(WebElement e : accSectionList) {
			accList.add(e.getText());
		}
		return accList;
	}
//	public List<String> getAccSectionLinks() {
//		List<String> accLinks = new ArrayList<>();
//		List<WebElement> accSectionList = driver.findElements(accSectionHeaders);
//		for(WebElement e : accSectionList) {
//			accLinks.add(e.getText());
//		}
//		return accLinks;
//	}
	
/**
 * this method only returns true or false. 
 * @param productName
 * @return
 */
	@Step("verifying account page search function with product name : {0} ")
	public boolean doSearch(String productName) {
		elementUtil.getElement(searchText).clear();
		elementUtil.doSendKeys(searchText, productName);
		elementUtil.doClick(searchButton);
		if(elementUtil.getElements(searchResult).size()>0) {
			return true;
		}
		return false;
	}
	
	@Step("selecting a product with name from results section: {0} ")
	public ProductInfoPage selectProductFromResults(String productName) {
		List<WebElement> resultItemsList = elementUtil.getElements(resultItems);
		System.out.println("total number of iteams found : " + resultItemsList.size());
		
		for(WebElement e : resultItemsList) {
			if(e.getText().equals(productName)) {
				e.click();
				break;
			}
		}
		
		return new ProductInfoPage(driver);
	}
	

}
