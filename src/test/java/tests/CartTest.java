package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.ConfigReader;

public class CartTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void initPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        ConfigReader config = ConfigReader.getInstance();
        inventoryPage = loginPage.login(config.getAppUsername(), config.getAppPassword());
    }

    @Test
    public void addFirstItemToCart() {
        CartPage cartPage = inventoryPage
                .addFirstItemToCart()
                .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1);
    }

    @Test
    public void removeFirstItemFromCart() {
        CartPage cartPage = inventoryPage
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), 0);
    }
}