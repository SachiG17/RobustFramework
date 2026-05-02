package org.RobustFramework.PageObjects;

import org.RobustFramework.Utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpLoginPage extends BasePage {

    public SignUpLoginPage(WebDriver driver, WaitUtils wait) {
        super(driver, wait);
    }

    // Locators
    private By username = By.name("name");
    private By usermail = By.xpath("//input[@data-qa='signup-email']");
    private By signUpBtn = By.xpath("//button[contains(text(),'Signup')]");

    // Actions
    public void fillDetails(String usernameVal, String usermailVal) {

        wait.waitForElementToBeVisible(username).sendKeys(usernameVal);
        wait.waitForElementToBeVisible(usermail).sendKeys(usermailVal);
        wait.smartClick(signUpBtn);
    }
}