package org.RobustFramework.PageObjects;

import org.RobustFramework.Utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver, WaitUtils wait) {
        super(driver, wait);
    }

    // Locators
    private By searchField = By.id("search_product");
    private By searchBtn = By.id("submit_search");
    private By addToCartBtn = By.xpath("(//a[contains(@class,'add-to-cart')])[1]");
    private By productName = By.xpath("//div[@class='productinfo text-center']/p");

    private By modal = By.xpath("//div[@class='modal-content']");
    private By viewCartBtn = By.xpath("//div[@class='modal-content']//a[@href='/view_cart']");

    // Actions
    public void searchProduct(String name) {
        wait.waitForElementToBeVisible(searchField).sendKeys(name);
        wait.smartClick(searchBtn);
    }

    public void verifyProduct(String expected) {
        String actual = wait.waitForElementToBeVisible(productName).getText();
        Assert.assertEquals(actual, expected);
    }

    public void addToCart() {
        wait.smartClick(addToCartBtn);
    }

    public void clickViewCart() {

        // wait for modal
        wait.waitForElementToBeVisible(modal);

        // smart click handles animation + intercept
        wait.smartClick(viewCartBtn);
    }
}