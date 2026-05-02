package org.RobustFramework.Tests;

import org.RobustFramework.PageObjects.HomePage;
import org.RobustFramework.PageObjects.LoginPage;
import org.RobustFramework.BaseTest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IncorrectLoginTest extends BaseTest {


    @Test(groups = {"LogInMatrix"})
    public void IncorrectLoginTest(){
        HomePage homePage=new HomePage(driver,waitUtils);
        homePage.goTo();
        homePage.clickSignInBtn();
        LoginPage loginPage = new LoginPage(driver,waitUtils);
        loginPage.logIn("sachinshetty@gmail.com","shettysach");
        String errormsg = loginPage.getErrorMessage();
        Assert.assertEquals("Your email or password is incorrect!",errormsg);
    }
}
