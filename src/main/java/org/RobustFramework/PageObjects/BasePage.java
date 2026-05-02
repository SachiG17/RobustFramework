package org.RobustFramework.PageObjects;


import org.RobustFramework.Utilities.WaitUtils;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;
    protected WaitUtils wait;

    public BasePage(WebDriver driver, WaitUtils wait) {
        if (driver == null) {
            throw new RuntimeException("Driver is NULL in BasePage");
        }
        this.driver = driver;
        this.wait = wait;
    }
}