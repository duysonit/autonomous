package ai.autonomous.page;

import ai.autonomous.util.Log;
import autonomous.automation.web.pageobject.MyPageObject;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://www.amazon.com/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%" +
        "2Fref%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc" +
        "_handle=usflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2" +
        ".0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&")

public class LoginAmazon extends MyPageObject {

    // Get element Email field by Xpath
    private static final String EMAIL_XPATH = "//*[@type='email']";

    // Get element Continue button by Xpath
    private static final String BUTTON_CONTINUE_XPATH = "//*[(@class='a-button-input') and (@type='submit')]";

    // Get element Password field by Xpath
    private static final String PASSWORD_XPATH = "//*[@type='password']";

    // Get element Sign-in button by Xpath
    private static final String BUTTON_SIGN_IN_XPATH = "//*[(@class='a-button-input') and (@type='submit')]";


    /**
     * Login by email
     */
    public void loginByEmail (String email, String password) throws InterruptedException{

        try {
            // Wait until element visible
            element(EMAIL_XPATH).waitUntilVisible();
            // Make sure email field to be clear
            element(EMAIL_XPATH).clear();
            // Input email
            element(EMAIL_XPATH).sendKeys(email);
            waitABit(1);
            // Click button continue
            element(BUTTON_CONTINUE_XPATH).click();
            // Wait until element visible
            element(PASSWORD_XPATH).waitUntilVisible();
            // Input psw
            element(PASSWORD_XPATH).sendKeys(password);
            waitABit(1);
            // Click button Sign-in
            element(BUTTON_SIGN_IN_XPATH).click();
            Log.highlight("Input email and password success !!!");
        }catch (Exception ex){
            Log.error("There has an error with sign in: " +ex);
        }

    }
}
