package ai.autonomous.steps.ui;

import ai.autonomous.page.PickTheFirstItem;
import net.thucydides.core.annotations.Step;

public class PickTheFirstItemSteps {
    PickTheFirstItem pickTheFirstItem;
    @Step
    public void pickItem() throws InterruptedException{
            pickTheFirstItem.pickTheFirstItemInListing();
    }
}
