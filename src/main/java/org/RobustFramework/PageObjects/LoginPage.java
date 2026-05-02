package org.RobustFramework.PageObjects;

import org.RobustFramework.Utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver, WaitUtils wait) {
        super(driver, wait);
    }

    // Locators
    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By loginBtn = By.xpath("//button[@data-qa='login-button']");
    private By errorMessage = By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

    // Actions
    public void logIn(String email, String password) {
        wait.waitForElementToBeVisible(emailField).sendKeys(email);
        wait.waitForElementToBeVisible(passwordField).sendKeys(password);
        wait.smartClick(loginBtn);
    }

    public String getErrorMessage() {
        return wait.waitForElementToBeVisible(errorMessage).getText();
    }
}