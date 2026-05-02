package org.RobustFramework.DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static void initializeDriver(BrowserType browserType, boolean isHeadless) {

        System.out.println("Initializing Browser: " + browserType + " | Headless: " + isHeadless);

        WebDriver driver = switch (browserType) {

            case CHROME -> new ChromeDriver(getChromeOptions(isHeadless));

            case EDGE -> {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--disable-notifications");

                if (isHeadless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                }

                yield new EdgeDriver(options);
            }

            case FIREFOX -> {
                FirefoxOptions options = new FirefoxOptions();

                if (isHeadless) {
                    options.addArguments("-headless");
                }

                yield new FirefoxDriver(options);
            }
        };

        threadDriver.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = threadDriver.get();

        if (driver == null) {
            throw new RuntimeException("Driver not initialized. Call initializeDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = threadDriver.get();

        if (driver != null) {
            driver.quit();
            threadDriver.remove(); // Prevent memory leak
        }
    }

    private static ChromeOptions getChromeOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        return options;
    }
}