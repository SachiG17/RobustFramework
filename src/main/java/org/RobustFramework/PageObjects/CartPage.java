package org.RobustFramework.PageObjects;

import org.RobustFramework.Utilities.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    WebDriver driver;
    private WaitUtils wait;
    public CartPage(WebDriver driver){
        this.driver=driver;
        this.wait=wait;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = ".check_out")
    WebElement CheckOutBtn;

    public void ProceedToCart(){
        CheckOutBtn.click();
    }
}
