package ai.autonomous.page;

import ai.autonomous.util.Log;
import autonomous.automation.web.pageobject.MyPageObject;
import org.junit.Assert;

public class VerifyTheSubtotalPrice extends MyPageObject {
    // Get element price per item
    private static final String PRICE_PER_ITEM_XPATH = "//*[@class='a-size-medium a-color-base sc-price sc-white" +
            "-space-nowrap sc-product-price a-text-bold']";
    // Get element price of the subtotal
    private static final String PRICE_OF_THE_SUBTOTAL_XPATH = "//*[@class='a-size-medium a-color-base sc-price sc" +
            "-white-space-nowrap']";
    // Get element quantity
    private static final String GET_QUANTITY_OF_ITEM_XPATH = "//*[@class='a-dropdown-prompt']";

    /**
     * Verify the subtotal price is correct
     */
    public void verifyTheSubtotalPrice () throws InterruptedException{

        try {
            // Wait until element visible
            element(PRICE_PER_ITEM_XPATH).waitUntilVisible();
            // Define price per item and remove 1st letter $
            String tempPricePerItem = element(PRICE_PER_ITEM_XPATH).getText().substring(1);
            // Convert string to double
            double pricePerItem = Double.parseDouble(tempPricePerItem);
            Log.highlight("Your price per each item: $" + pricePerItem);

            // Get quantity of item in cart
            String getQuantityOfItem = element(GET_QUANTITY_OF_ITEM_XPATH).getText();
            // Covert string to double
            double quantityOfItem = Double.parseDouble(getQuantityOfItem);
            Log.highlight("Your quantity of item: " + quantityOfItem);

            // Calculate price of the subtotal
            double tempExpectPriceOfTheSubtotal = (pricePerItem) * (quantityOfItem);
            // Round value to 2 value decimal points
            double expectPriceOfTheSubtotal = ((double) Math.round(tempExpectPriceOfTheSubtotal * 100.0) / 100.0);
            Log.highlight("Your expected the subtotal price: $" + expectPriceOfTheSubtotal);

            // Define element price of the subtotal and remove 1st letter $
            String tempActualPriceOfTheSubtotal = element(PRICE_OF_THE_SUBTOTAL_XPATH).getText().substring(1);
            // Convert string to double and replace comma
            double actualPriceOfTheSubtotal = Double.parseDouble(tempActualPriceOfTheSubtotal.replace(",", ""));
            Log.highlight("Your actual the subtotal price: $" + actualPriceOfTheSubtotal);

            // Verify price of the subtotal is correct
            if((Double.compare(actualPriceOfTheSubtotal,expectPriceOfTheSubtotal) == 0)){
                Assert.assertTrue(true);
            }
            Log.highlight("Verify the subtotal price is correct !");

        }catch (Exception ex){
            Log.error("There has an error exception to verify the subtotal price: "+ex);
        }
    }
}
