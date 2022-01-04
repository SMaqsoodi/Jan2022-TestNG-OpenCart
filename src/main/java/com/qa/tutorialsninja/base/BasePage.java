package com.qa.tutorialsninja.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.tutorialsninja.utils.OptionsManager;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author saeed
 *
 */
public class BasePage {
	public WebDriver driver;
	public Properties prop;
	
// we use the concept of ThreadLocal for the base page so on parallel test execution in xml file,
// it creates local copies of the driver for each thread with the same session ID.
// ThreadLocal object has to important methods set() and get()
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	// to add headless and incognito ability we use OptionsManager class that we created in utils package.
	public OptionsManager optionsManager;
	public static String highlight;
		
/**
 * This method initialize the browser on the basis of given browser
 * @param browser
 * @return this method returns WebDrive driver
 */
	public WebDriver init_driver(String browser, String browserVersion) {
		System.out.println("browser value is : " + browser);
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		switch (browser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				// if running tests on remote: 
				// if(prop.getProperty("remote").equals("true")) {
				// } or similarly
				if (Boolean.parseBoolean(prop.getProperty("remote"))) {
					init_remoteWebDriver(browser, browserVersion);
				} 
				else {
					tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				}
		
				//driver = new ChromeDriver();
				break;
			
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				if (Boolean.parseBoolean(prop.getProperty("remote"))) {
					init_remoteWebDriver(browser, browserVersion);
				} 
				else {
					tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
				}
				//driver = new FirefoxDriver();
				break;
			case "safari":
				tlDriver.set(new SafariDriver());
				//driver = new SafariDriver();
				break;
			case "EI":
				WebDriverManager.iedriver().setup();
				tlDriver.set(new InternetExplorerDriver());
				//driver = new InternetExplorerDriver();
				break;
				
			default:
				System.out.println("please enter a valid browser");
				break;	
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		
		return getDriver();
	}


/**
 * getDriver using ThreadLocal
 */
	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}	
	
/**
 * this method is used when we run our tests on remote machine, vm or containers when remote=true in config.properties	
 * @param string
 */
	private void init_remoteWebDriver(String browser, String browserVersion) {
		System.out.println("running test on remote grid: " + browser);
		if (browser.equalsIgnoreCase("chrome")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
			cap.setCapability("browserName", "chrome");
			cap.setCapability("browserVersion", browserVersion);
			cap.setCapability("enableVNC", true);
			
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else if (browser.equalsIgnoreCase("firefox")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFireFoxOptions());
			cap.setCapability("browserName", "firefox");
			cap.setCapability("browserVersion", browserVersion);
			cap.setCapability("enableVNC", true);
			
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		
}



		
/**
 * this method is used to make connection to and load properties from config.properties file to avoid hard coded values 
 * @return it returns Properties prop reference 
 */
	public Properties init_prop() {
		prop = new Properties(); // we declare this properties ref in class level so it can be used by other classes also then we initialize it here
		try {
			FileInputStream ip = new FileInputStream("./src/main/java/com/qa/tutorialsninja/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
		
	}
	
/**
 * this method is used to take screenshot. and Selenium is responsible to take the screenshot
 * it will return the path of screenshot
 */
// TakesScreenshot is an Interface from Selenium
	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
// at this stage, our source file object is some where in the memory
// then we have to create a path to save the file on the disk. 
// user.dir means current project directory, then if /screenshots/ is not there it will be created automatically
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
// so we create one more File object using the created path to copy source file like this: 	
// this new file is our destination file
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
		
	}

	
}
