package ai.autonomous.page;

import ai.autonomous.util.Config;
import ai.autonomous.util.Log;
import autonomous.automation.web.pageobject.MyPageObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class SearchFilterAndSortItem extends MyPageObject {

    // Get element search box by Xpath
    private static final String SEARCH_BOX_XPATH = "//*[@id='twotabsearchtextbox']";
    // Get element capacity 64GB
    private static final String CHECKBOX_CAPACITY_64GB_XPATH = "//*[(@class='a-size-base a-color-base') and " +
                                                                                "(contains(text(),'64 GB'))]";
    // Get element sort dropdown
    private static final String DROPDOWN_SORT_XPATH = "//*[@class='a-dropdown-prompt']";
    // Get element sort by price from high to low
    private static final String SORT_BY_PRICE_XPATH = "//*[contains(text(),'Price: High to Low')]";

    /**
     * Search for item sd card
     */
    public void searchItem () throws InterruptedException{
        try {
            // Import action interaction
            Actions actions = new Actions(getDriver());
            // Wait until element visible
            element(SEARCH_BOX_XPATH).waitUntilVisible();
            // Make sure search box to be clear
            element(SEARCH_BOX_XPATH).clear();
            // Search for item "sd card"
            element(SEARCH_BOX_XPATH).sendKeys(Config.SEARCH_KEYWORD);
            waitABit(1);
            // Enter to search
            actions.sendKeys(Keys.ENTER).build().perform();
            waitABit(1);
            Log.highlight("Search for item sd card success !!!");
        }catch (Exception ex){
            Log.error("There has an error to search: "+ex);
        }

    }

    /**
     * Filter capacity 64GB
     */
    public void filterCapacityItem () throws InterruptedException{

        try {
            // Wait until element visible
            element(CHECKBOX_CAPACITY_64GB_XPATH).waitUntilVisible();
            // Filter capacity 64GB
            element(CHECKBOX_CAPACITY_64GB_XPATH).click();
            Log.highlight("Filter capacity 64GB for sd card success !!!");
        }catch (Exception ex){
            Log.error("There has an error to filter: "+ex);
        }
    }
    /**
     * Sort by price from high to low
     */
    public void sortByPrice() throws InterruptedException{
        try {
            // Wait until element visible
            element(DROPDOWN_SORT_XPATH).waitUntilVisible();
            // Sort by price from high to low
            element(DROPDOWN_SORT_XPATH).click();
            // Wait until element visible
            element(SORT_BY_PRICE_XPATH).waitUntilVisible();
            // Click sort by price from high to low
            element(SORT_BY_PRICE_XPATH).click();
            Log.highlight("Sort by price from high to low success !!!");
        }catch (Exception ex){
            Log.error("There has an error to sort by price: "+ex);
        }
    }
}
