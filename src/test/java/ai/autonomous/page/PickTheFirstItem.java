package ai.autonomous.page;

import ai.autonomous.util.Log;
import autonomous.automation.web.pageobject.MyPageObject;

public class PickTheFirstItem extends MyPageObject {
    // Get element pick 1st item in listing
    private static final String PICK_1ST_ITEM = "//*[@data-image-index='1']";

    /**
     * Pick the 1st item in listing
     */
    public void pickTheFirstItemInListing () throws InterruptedException{
        try {
            // Wait until element visible
            element(PICK_1ST_ITEM).waitUntilVisible();
            // Picl 1st item in listing
            element(PICK_1ST_ITEM).click();
            Log.highlight("Pick the 1st item in listing success !!!");
        }catch (Exception ex){
            Log.error("Can't pick the first item: "+ex);
        }
    }
}
