package org.RobustFramework.Tests;
import org.RobustFramework.PageObjects.HomePage;
import org.RobustFramework.PageObjects.LoginPage;
import org.RobustFramework.BaseTest.BaseTest;
import org.testng.annotations.Test;
import java.io.IOException;

public class LoginTest extends BaseTest {
    HomePage homePage;


    @Test(groups = {"LogInMatrix"})
    public void LogInTest() throws IOException {
        homePage = new HomePage(driver,waitUtils);
        homePage.goTo();
        homePage.clickSignInBtn();
        LoginPage loginPage = new LoginPage(driver,waitUtils);
        loginPage.logIn("sachinshetty@gmail.com","shettysachin");
        homePage.verifyLoggedIn();
        homePage.logOut();
    }

}
