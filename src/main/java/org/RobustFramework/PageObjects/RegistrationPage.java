package org.RobustFramework.PageObjects;

import org.RobustFramework.Utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver, WaitUtils wait) {
        super(driver, wait);
    }

    // Locators
    private By signUpPassword = By.id("password");
    private By firstName = By.id("first_name");
    private By lastName = By.id("last_name");
    private By address1 = By.id("address1");
    private By countryEle = By.name("country");
    private By state = By.name("state");
    private By city = By.name("city");
    private By zipcode = By.id("zipcode");
    private By mobileNumber = By.name("mobile_number");
    private By createBtn = By.xpath("//button[contains(text(),'Create Account')]");

    // Actions
    public void fillRegistrationForm(String passVal, String firstNameVal, String lastNameVal,
                                     String add1Val, String countryVal, String stateVal,
                                     String cityVal, String zipcodeVal, String mblNum) {

        wait.waitForElementToBeVisible(signUpPassword).sendKeys(passVal);
        wait.waitForElementToBeVisible(firstName).sendKeys(firstNameVal);
        wait.waitForElementToBeVisible(lastName).sendKeys(lastNameVal);
        wait.waitForElementToBeVisible(address1).sendKeys(add1Val);

        // Select dropdown
        WebElement countryDropdown = wait.waitForElementToBeVisible(countryEle);
        Select country = new Select(countryDropdown);
        country.selectByVisibleText(countryVal);   // better than value

        wait.waitForElementToBeVisible(state).sendKeys(stateVal);
        wait.waitForElementToBeVisible(city).sendKeys(cityVal);
        wait.waitForElementToBeVisible(zipcode).sendKeys(zipcodeVal);
        wait.waitForElementToBeVisible(mobileNumber).sendKeys(mblNum);

        wait.smartClick(createBtn);
    }
}