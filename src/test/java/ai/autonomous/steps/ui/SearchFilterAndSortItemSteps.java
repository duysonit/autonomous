package ai.autonomous.steps.ui;

import ai.autonomous.page.SearchFilterAndSortItem;
import net.thucydides.core.annotations.Step;

public class SearchFilterAndSortItemSteps {

    SearchFilterAndSortItem searchFilterAndSortItem;
    @Step
    public void searchItem() throws InterruptedException{
        searchFilterAndSortItem.searchItem();
    }
    @Step
    public void filterCapacityItem() throws InterruptedException{
        searchFilterAndSortItem.filterCapacityItem();
    }
    @Step
    public void sortByPrice() throws InterruptedException{
        searchFilterAndSortItem.sortByPrice();
    }

}
