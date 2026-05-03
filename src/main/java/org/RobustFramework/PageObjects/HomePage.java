package org.RobustFramework.PageObjects;

import org.RobustFramework.Utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver, WaitUtils wait) {
        super(driver, wait);
    }

    // Locators
    private By loginBtn = By.cssSelector("a[href='/login']");
    private By loggedInText = By.xpath("//a[contains(normalize-space(.),'Logged in as')]");
    private By logoutBtn = By.linkText("Logout");
    private By continueBtn = By.linkText("Continue");
    private By deleteBtn = By.linkText("Delete Account");
    private By accountCreatedMsg = By.xpath("//b[contains(text(),'Account Created!')]");
    private By deleteMsg = By.xpath("//b[contains(text(),'Account Deleted!')]");
    private By productBtn = By.xpath("//a[contains(text(),'Products')]");
    private By cartBtn = By.xpath("//a[contains(text(),'Cart')]");

    // Actions
    public void goTo() {
        driver.get("https://automationexercise.com/");
    }

    public void clickSignInBtn() {
        wait.smartClick(loginBtn);
    }

    public void verifyLoggedIn() {
        String text = wait.waitForElementToBeVisible(loggedInText).getText();
        Assert.assertTrue(text.contains("Logged in as"), "Login message not found");
    }

    public void clickContinue() {
        wait.smartClick(continueBtn);
    }

    public void logOut() {
        wait.smartClick(logoutBtn);
    }

    public void deleteProfile() {
        wait.smartClick(deleteBtn);
    }

    public void verifyProfileCreated() {
        String text = wait.waitForElementToBeVisible(accountCreatedMsg).getText();
        Assert.assertEquals(text, "ACCOUNT CREATED!");
    }

    public void verifyProfileDeleted() {
        String text = wait.waitForElementToBeVisible(deleteMsg).getText();
        Assert.assertEquals(text, "ACCOUNT DELETED!");
    }

    public void openProductPage() {
        wait.smartClick(productBtn);
    }

    public void openCart() {
        wait.smartClick(cartBtn);
    }
}