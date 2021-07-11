package ai.autonomous.page;

import ai.autonomous.util.Log;
import autonomous.automation.web.pageobject.MyPageObject;

public class AddToCart extends MyPageObject {
    // Get element add to cart
    private static final String ADD_TO_CART_XPATH = "//*[@id='add-to-cart-button']";
    // Get element submit add to cart
    private static final String SUBMIT_ADD_TO_CART_XPATH = "//*[(@class='a-button-input') and " +
            "(@aria-labelledby='attachSiNoCoverage-announce')]";


    /**
     * Add to cart
     */
    public void addToCart () throws InterruptedException{
        try {
            // Wait until element visible
            element(ADD_TO_CART_XPATH).waitUntilVisible();
            // Click button add to cart
            element(ADD_TO_CART_XPATH).click();
            waitABit(1);
            try {
                // Wait until element visible
                element(SUBMIT_ADD_TO_CART_XPATH).waitUntilVisible();
                // Click button No thanks
                element(SUBMIT_ADD_TO_CART_XPATH).click();
            }catch (Exception e){
                Log.info("There has no need to submit add to cart: " +e);
            }
            Log.highlight("Add to cart success !!!");
        }catch (Exception ex){
            Log.error("There has an error exception to add to cart: "+ex);
        }

    }
}
