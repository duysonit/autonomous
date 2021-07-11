package ai.autonomous.page;

import ai.autonomous.util.Log;
import autonomous.automation.web.pageobject.MyPageObject;

public class ViewTheCart extends MyPageObject {
    // Get element view to cart
    private static final String VIEW_THE_CART_XPATH = "//*[@id='hlb-view-cart-announce']";
    // Get element quantity
    private static final String QUANTITY_XPATH = "//*[(@class='a-dropdown-label') and (contains(text(),'Qty:'))]";
    // Get element increase quantity
    private static final String INCREASE_QUANTITY_XPATH = "//*[@id='dropdown1_3']";

    /**
     * View the cart
     */
    public void viewTheCart () throws InterruptedException{
        try {
            // Wait until element visible
            element(VIEW_THE_CART_XPATH).waitUntilVisible();
            // Click view cart
            element(VIEW_THE_CART_XPATH).click();
            // Wait until element visible
            element(QUANTITY_XPATH).waitUntilVisible();
            // Click to show dropdown quantity
            element(QUANTITY_XPATH).click();
            // Wait until element visible
            element(INCREASE_QUANTITY_XPATH).waitUntilVisible();
            // Increase quantity to 3
            element(INCREASE_QUANTITY_XPATH).click();
            Log.highlight("View the cart and increase quantity to 3 success !!!");

        }catch (Exception ex){
            Log.error("There has an error exception to view the cart: "+ex);
        }
    }
}
