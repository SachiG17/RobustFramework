package org.RobustFramework.Tests;
import org.RobustFramework.PageObjects.CartPage;
import org.RobustFramework.PageObjects.HomePage;
import org.RobustFramework.PageObjects.LoginPage;
import org.RobustFramework.PageObjects.ProductPage;
import org.RobustFramework.BaseTest.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {
    @Test(dataProvider = "getData")
    public  void AddProductandVerify(String productName){
        HomePage homePage=new HomePage(driver,waitUtils);
        homePage.goTo();
        homePage.clickSignInBtn();
        LoginPage loginPage = new LoginPage(driver,waitUtils);
        loginPage.logIn("sachinshetty@gmail.com","shettysachin");
        homePage.openProductPage();
        ProductPage productPage = new ProductPage(driver,waitUtils);
        productPage.searchProduct(productName);
        productPage.verifyProduct(productName);
        productPage.addToCart();
       productPage.clickViewCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.ProceedToCart();
    }
    @DataProvider
    public Object[] getData(){
        return new Object[][]{{"Blue Top"},{"Men Tshirt"}};

    }
}
