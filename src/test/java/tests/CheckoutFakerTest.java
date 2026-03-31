package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.TestDataFactory;
import utils.ConfigReader;

import java.util.Map;

public class CheckoutFakerTest extends BaseTest {

    @Test
    public void checkoutWithRandomData() {
        LoginPage loginPage = new LoginPage(getDriver());
        ConfigReader config = ConfigReader.getInstance();

        InventoryPage inventoryPage = loginPage.login(
            config.getAppUsername(),
            config.getAppPassword()
        );
        CartPage cartPage = inventoryPage
                .addFirstItemToCart()
                .goToCart();

        CheckoutPage checkoutPage = cartPage.goToCheckout();

        Map<String, String> data = TestDataFactory.randomCheckoutData();

        System.out.println("Random checkout data:");
        System.out.println("First Name: " + data.get("firstName"));
        System.out.println("Last Name: " + data.get("lastName"));
        System.out.println("Postal Code: " + data.get("postalCode"));

        checkoutPage.fillCheckoutForm(data);
        checkoutPage.clickContinue();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-two"),
                "Checkout step two is not displayed");
    }
}