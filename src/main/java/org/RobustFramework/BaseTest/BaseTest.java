package org.RobustFramework.BaseTest;

import org.RobustFramework.DriverFactory.BrowserType;
import org.RobustFramework.DriverFactory.DriverFactory;
import org.RobustFramework.Utilities.ConfigReader;
import org.RobustFramework.Utilities.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected WaitUtils waitUtils;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.getBrowser();
        boolean isHeadless = ConfigReader.isHeadless();
        log.info("Reading browser from config: " + browser);
        if (browser == null || browser.isEmpty()) {
            log.error("Browser is missing in config file");
            throw new RuntimeException("Browser is missing in config file");
        }
        BrowserType browserType;
        try {
            browserType = BrowserType.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid browser: " + browser);
            throw new RuntimeException("Invalid browser in config: " + browser);
        }
        log.info("Initializing driver for browser: " + browserType);
        DriverFactory.initializeDriver(browserType,isHeadless);
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        waitUtils = new WaitUtils(driver);
        log.info("Browser launched and maximized");
    }

    @AfterMethod
    public void tearDown() {
        log.info("Closing browser");
        DriverFactory.quitDriver();
    }

    protected WebDriver getDriver() {

        return DriverFactory.getDriver();
    }


    protected void openUrl(String url) {
        if (url == null || url.isEmpty()) {
            log.error("URL is null or empty");
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        log.info("Opening URL:" + url);
        getDriver().get(url);
    }
}