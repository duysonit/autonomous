package ai.autonomous.steps.ui;

import ai.autonomous.page.AddToCart;
import net.thucydides.core.annotations.Step;

public class AddToCartSteps {
    AddToCart addToCart;
    @Step
    public void addToCart() throws InterruptedException{
        addToCart.addToCart();
    }
}
