package org.RobustFramework.Tests;
import org.RobustFramework.PageObjects.HomePage;
import org.RobustFramework.PageObjects.LoginPage;
import org.RobustFramework.BaseTest.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.RobustFramework.Utilities.JsonUtils.getJsonData;

public class LoginTestDDT extends BaseTest {
    HomePage homePage;

    @Test(dataProvider ="getData",groups = {"LogInMatrix"})
    public void LogInTest(HashMap<String,String> input) throws IOException {
        homePage = new HomePage(driver,waitUtils);
        homePage.goTo();
        homePage.clickSignInBtn();
        LoginPage loginPage = new LoginPage(driver,waitUtils);
        loginPage.logIn(input.get("email"),input.get("password"));
        homePage.verifyLoggedIn();
        homePage.logOut();
    }
    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String,String>> data = getJsonData(System.getProperty("user.dir")+"//src//test//resources//Data//MultipleLoginDetails.json");
        return new Object[][] {{data.get(0)},{data.get(1)}};
    }
}
