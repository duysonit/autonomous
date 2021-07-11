package ai.autonomous.features.ui;

import ai.autonomous.steps.ui.*;
import ai.autonomous.util.Config;
import ai.autonomous.util.Log;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityRunner.class)
@WithTag(type = "ui",name = "amazon")
public class AmazonTesting {
    @Managed
    WebDriver driver;

    @Steps
    LoginAmazonSteps loginAmazonSteps;

    @Steps
    SearchFilterAndSortItemSteps searchFilterAndSortItemSteps;

    @Steps
    PickTheFirstItemSteps pickTheFirstItemSteps;

    @Steps
    AddToCartSteps addToCartSteps;

    @Steps
    ViewTheCartSteps viewTheCartSteps;

    @Steps
    VerifyTheSubtotalPriceSteps verifyTheSubtotalPriceSteps;

    @Before
    public void beforeMethod() throws Exception {

        try {
            // Open page to login
            loginAmazonSteps.openLoginPage();
            Log.highlight("Open page to login success");
            // Sign in
            loginAmazonSteps.loginByEmailPage(Config.EMAIL_AMAZON, Config.PASSWORD_AMAZON);
            Log.highlight("Sign in success!!!");
        }catch (Exception ex){
            Log.highlight("Cannot to sign in ! " +ex);
        }

    }

    @Test
    public void doTestingAmazon() throws Exception {
        // Search item sd card
        searchFilterAndSortItemSteps.searchItem();
        // Filter capacity 64GB sd card
        searchFilterAndSortItemSteps.filterCapacityItem();
        // Sort by price from high to low
        searchFilterAndSortItemSteps.sortByPrice();
        // Pick the first item in listing
        pickTheFirstItemSteps.pickItem();
        // Add to cart
        addToCartSteps.addToCart();
        // View the cart and increase quantity to 3
        viewTheCartSteps.viewTheCart();
        // Verify the subtotal price
        verifyTheSubtotalPriceSteps.verifyTheSubtotalPrice();


    }

    @AfterClass
    public static void tearDown() {

    }
}
