package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void loginSuccess() {
        ConfigReader config = ConfigReader.getInstance();
        InventoryPage inventoryPage = loginPage.login(
                config.getAppUsername(),
                config.getAppPassword()
        );
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page is not loaded");
    }

    @Test
    public void loginFailWrongPassword() {
        ConfigReader config = ConfigReader.getInstance();
        loginPage.loginExpectingFailure(config.getAppUsername(), "wrong_password");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message is not displayed");
    }
}