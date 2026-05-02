package org.RobustFramework.Tests;
import org.RobustFramework.BaseTest.BaseTest;
import org.RobustFramework.PageObjects.HomePage;
import org.RobustFramework.PageObjects.RegistrationPage;
import org.RobustFramework.PageObjects.SignUpLoginPage;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class UserRegistrationTest extends BaseTest {
    HomePage homePage;

    @Test
    public void RegisterDeleteUserTest() throws IOException {
        HomePage homePage = new HomePage(driver,waitUtils);
        homePage.goTo();
        homePage.clickSignInBtn();
        SignUpLoginPage signUpLoginPage = new SignUpLoginPage(driver,waitUtils);
        signUpLoginPage.fillDetails("Sachin","SachinxydfszzMzzz@gmail.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        RegistrationPage registrationPage = new RegistrationPage(driver,waitUtils);
        registrationPage.fillRegistrationForm("Shetty@143","Sachin","Shetty","At post Hosur","United States","Goa","Goa","123456","9008953628");
        homePage.verifyProfileCreated();
        homePage.clickContinue();
        homePage.deleteProfile();
        homePage.verifyProfileDeleted();
    }

}
