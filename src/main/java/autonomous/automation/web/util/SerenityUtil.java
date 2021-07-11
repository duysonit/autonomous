/*
 * Liquid Pay
 */
package autonomous.automation.web.util;

import net.thucydides.core.guice.ThucydidesModule;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.util.EnvironmentVariables;

public class SerenityUtil {

    private static EnvironmentVariables evs;

    public static String getEnv(String key) {
        if (evs == null) {
            evs = (new ThucydidesModule()).provideEnvironmentVariables();
        }
        return evs.getProperty(key)==null?"":evs.getProperty(key);
    }

    public static String getIssue(){

        return StepEventBus.getEventBus().getBaseStepListener().getTestOutcomes().get(0).getIssueKeys().get(0);
    }

    public static String getBaseUrl() {
        return getEnv("webdriver.base.url");
    }
    
    public static String getHttpsBaseUrl(){
        String baseURL = SerenityUtil.getBaseUrl();
        if (!baseURL.contains("https")) {
            return baseURL.replaceAll("http", "https");
        }
        return baseURL;
    }

}
