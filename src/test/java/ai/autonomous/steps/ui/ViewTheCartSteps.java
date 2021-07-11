package ai.autonomous.steps.ui;

import ai.autonomous.page.AddToCart;
import ai.autonomous.page.ViewTheCart;
import net.thucydides.core.annotations.Step;

public class ViewTheCartSteps {
    ViewTheCart viewTheCart;

    @Step
    public void viewTheCart() throws InterruptedException {
        viewTheCart.viewTheCart();
    }
}
