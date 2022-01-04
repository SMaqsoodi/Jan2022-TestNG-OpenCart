package com.qa.tutorialsninja.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.tutorialsninja.pages.AccountPage;
import com.qa.tutorialsninja.pages.CartPage;
import com.qa.tutorialsninja.pages.LoginPage;
import com.qa.tutorialsninja.pages.ProductInfoPage;
import com.qa.tutorialsninja.pages.RegisterPage;



public class BaseTest {
	public BasePage basePage; // creating on object of the BasePage. no need to import since they are in the same package
	public Properties prop;
	public WebDriver driver;
	public LoginPage loginPage;
	public AccountPage accountPage;
	public ProductInfoPage productInfoPage;
	public CartPage cartPage;
	public RegisterPage registerPage;
	
	@Parameters({"browser", "version"})
	@BeforeTest
	public void setUp(String browserName, String browserVersion) { // browserName is passed from the test_runner.xml as parameter
		basePage = new BasePage();
		prop = basePage.init_prop();
		String browser = prop.getProperty("browser");  // this method returns the value of the key in config.properties
		
		if(browserName!=null) {
			browser = browserName;
		}
		
		driver = basePage.init_driver(browser, browserVersion);
		loginPage = new LoginPage(driver);
		driver.get(prop.getProperty("url"));
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
