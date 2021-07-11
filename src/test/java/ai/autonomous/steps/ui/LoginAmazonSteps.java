package ai.autonomous.steps.ui;

import ai.autonomous.page.LoginAmazon;
import net.thucydides.core.annotations.Step;

public class LoginAmazonSteps {

    LoginAmazon loginAmazon;
    @Step
    public void openLoginPage(){
        loginAmazon.open();

    }
    @Step
    public void loginByEmailPage(String email, String password) throws InterruptedException{
        loginAmazon.loginByEmail(email,password);

    }
}
