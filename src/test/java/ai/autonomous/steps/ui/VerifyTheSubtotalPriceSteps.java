package ai.autonomous.steps.ui;

import ai.autonomous.page.VerifyTheSubtotalPrice;
import net.thucydides.core.annotations.Step;

public class VerifyTheSubtotalPriceSteps {
    VerifyTheSubtotalPrice verifyTheSubtotalPrice;

    @Step
    public void verifyTheSubtotalPrice() throws InterruptedException {
        verifyTheSubtotalPrice.verifyTheSubtotalPrice();
    }
}
