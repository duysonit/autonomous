/*
 * Liquid Pay
 */
package autonomous.automation.web.pageobject;

import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MyPageObject extends PageObject {

    public WebElement findFirstDisplayedElement(WebElementFacade parentEl, By by) {
        if (parentEl == null) {
            List<WebElementFacade> listEl = findAll(by);
            for (WebElementFacade el : listEl) {
                if (el.isDisplayed()) {
                    return el;
                }
            }
            return null;
        } else{
            List<WebElement> listEl = parentEl.findElements(by);
            for (WebElement el : listEl) {
                if (el.isDisplayed()) {
                    return el;
                }
            }
            return null;
        }
    }
    
    public WebElement findFirstDisplayedElement(By by){
        return findFirstDisplayedElement(null, by);
    }
    
    public void scrollIntoView(WebElementFacade webElementFacade){
        scrollIntoView(webElementFacade.getWrappedElement());
    }
    
    public void scrollIntoView(WebElement webElement){
        evaluateJavascript("arguments[0].scrollIntoView(true);",webElement); 
    }
    
    public void setAttributeValue(WebElementFacade el, String value) {
        evaluateJavascript("arguments[0].value=arguments[1];", 
                el.getWrappedElement(), value);
    }

    @Override
    public void clickOn(WebElement webElement) {
        if(!webElement.isDisplayed()){
            scrollIntoView(webElement);
        }
        waitABit(2);
        super.clickOn(webElement);
    }

    @Override
    public void typeInto(WebElement field, String value) {
        if(!$(field).isCurrentlyVisible()){
            scrollIntoView(field);
        }
        super.typeInto(field, value);
    }
    
    public String getText(WebElement field){
        if($(field).isCurrentlyVisible()){
            return field.getText();
        } else{
            return field.getAttribute("textContent").replaceAll("\\s+", " ").trim();
        }
    }

    public String getText(String xpath){
        if(xpath==null){
            return "";
        }
        return getText((WebElement) find(By.xpath(xpath)));
    }
    
   public boolean isElementPresent(WebElement parentEl, By by){
        if(parentEl==null){
            return findAll(by).size()>0;
        } else {
            return parentEl.findElements(by).size()>0;
        }
    }
    
    public boolean isElementPresent(By by){
        return isElementPresent(null, by);
    }

    @Override
    public void waitABit(long timeInSecond) {
        super.waitABit(timeInSecond*1000);
    }
    
    public WebElement findWebEl(String xpathOrCssSelector) {
//        By by = Selectors.xpathOrCssSelector(xpathOrCssSelector);
        return $(xpathOrCssSelector);
    }
    
    public void uploadFile(WebElement inputField, String fileName){
        if(!inputField.isDisplayed()){
            scrollIntoView(inputField);
        }
        inputField.sendKeys(fileName);
    }

    public void gotoPage(String url){
        getDriver().get(url);
    }

    public void goBack(){
        getDriver().navigate().back();
    }
    
    public void clickButton(String text){
        find(By.xpath("//button[text()='"+text+"']")).click();
    }

    public void clickWhenReady(By by){
        withTimeoutOf(10, TimeUnit.SECONDS)
                .elementIsDisplayed(by);
        element(by).click();
    }

    public AndroidDriver getAndroidDriver(){
        return (AndroidDriver) ((WebDriverFacade) getDriver()).getProxiedDriver();
    }

    /**
     * Check that the specified text appears somewhere in the page.
     */
    public void shouldContainText(WebElementFacade element, final String textValue) {
        if (!element.containsText(textValue)) {
            String errorMessage = String.format(
                    "The text '%s' was not found in the page", textValue);
            throw new NoSuchElementException(errorMessage);
        }
    }

    public void selectByText(WebElementFacade element,String text){
        scrollIntoView(element);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public void selectByIndex(WebElementFacade element, int index){
        scrollIntoView(element);
        Select select = new Select(element);
        select.selectByIndex(index);
    }

}
