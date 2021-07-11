package autonomous.automation.web.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;


public class Browsers {

    /**
     * Navigate to the link in Firefox browser (this link doesn't require
     * Credentials)
     */
    public WebDriver FFBrowser(String link) {
        WebDriver driver;
        driver = new FirefoxDriver();
        driver.get("http://" + link);
        return driver;
    }

    /**
     * Navigate to the link in Firefox browser (this link doesn't require
     * Credentials)
     */
    public WebDriver FFBrowserHTTPS(String link) {
        WebDriver driver;
        driver = new FirefoxDriver();
        driver.get("https://" + link);
        return driver;
    }

    /**
     *
     * @param link
     */
    public WebDriver SafariBrowser(String link, String driverDir) {
        WebDriver driver;
        /*System.setProperty("webdriver.safari.driver", driverDir);
		DesiredCapabilities Cap = DesiredCapabilities.safari();		
		driver = new SafariDriver(Cap);*/
        driver = new SafariDriver();
        driver.get("http://" + link);
        return driver;
    }

    /**
     * Navigate to the link in Firefox browser (this link requires Credentials
     * by usr/pwd)
     */
    public WebDriver FFAuthentication(String link, String usr, String pwd) throws AWTException {
        WebDriver driver;
        driver = new FirefoxDriver();
        driver.get("http://" + usr + ":" + pwd + "@" + link);
        return driver;
    }

    /**
     * Navigate to the link in Firefox browser with an option downloading
     * automatically into dir path.
     */
    public WebDriver FFProfileBrowser(String link, String dir) {

        WebDriver driver;

        FirefoxProfile profile = new FirefoxProfile();

        profile.setPreference("browser.download.folderList", 2);

        profile.setPreference("browser.download.dir", dir);

        profile.setPreference("browser.download.manager.showWhenStarting", false);

        profile.setPreference(
                "browser.helperApps.neverAsk.saveToDisk",
                "application/zip");

        driver = new FirefoxDriver(profile);

        driver.get("http://" + link);

        return driver;

    }

    /**
     * Navigate to the link in IE browser with an option downloading file into
     * known path.
     */
    public WebDriver IEProfileBrowser(String link, String driverFile, String dir) {
        WebDriver driver;
        File file = new File(driverFile);
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
        cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        driver = new InternetExplorerDriver(cap);
        driver.navigate().to("http://" + link);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setDownloadDir(dir);//Use Robot to define the download path for IR browser
        return driver;
    }

    /**
     * Navigate to an URL by FF browser via hub
     */
    public WebDriver FFRemote(String link, String usr, String pwd, String port) throws MalformedURLException {
        WebDriver driver;
        DesiredCapabilities capability = null;
        capability = DesiredCapabilities.firefox();
        capability.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
        capability.setPlatform(org.openqa.selenium.Platform.ANY);
        driver = new RemoteWebDriver(new URL("http://localhost:" + port + "/wd/hub"), capability);
        driver.get("http://" + usr + ":" + pwd + "@" + link);
        return driver;
    }

    /**
     * Prepare a remote Firefox browser
     */
    public WebDriver FFRemote(String port) throws MalformedURLException {
        WebDriver driver;
        DesiredCapabilities capability = null;
        capability = DesiredCapabilities.firefox();
        capability.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
        capability.setPlatform(org.openqa.selenium.Platform.ANY);
        driver = new RemoteWebDriver(new URL("http://localhost:" + port + "/wd/hub"), capability);
        return driver;
    }

    /**
     * Navigate to the link in Safari browser (this link requires Credentials by
     * usr/pwd)
     */
    public WebDriver SafariAuthentication(String link, String usr, String pwd, String driverDir) throws AWTException {
        WebDriver driver;
        driver = new SafariDriver();

        driver.get("http://" + usr + ":" + pwd + "@" + link);
        return driver;
    }

    /**
     * Navigate to the link in Safari browser via hub(this link requires
     * Credentials by usr/pwd)
     */
    public WebDriver SafariRemote(String link, String usr, String pwd, String port) throws MalformedURLException {
        WebDriver driver;
        DesiredCapabilities capability = null;
        capability = DesiredCapabilities.safari();
        capability.setBrowserName("safari");
        capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
        driver = new RemoteWebDriver(new URL("http://localhost:" + port + "/wd/hub"), capability);
        driver.get("http://" + usr + ":" + pwd + "@" + link);
        return driver;
    }

    /**
     * Navigate to the link by IE browser (without credentials)
     */
    public WebDriver IEBrowser(String link, String driverFile) {
        WebDriver driver;
        File file = new File(driverFile);
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
        cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        cap.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "ignore");

        driver = new InternetExplorerDriver(cap);
        driver.navigate().to("http://" + link);
        return driver;
    }

    /**
     * Set Download location to IE using Robot.
     */
    private void setDownloadDir(String path) {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_J);
            robot.keyRelease(KeyEvent.VK_J);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(1500);
            for (int i = 0; i < 12; i++) {
                robot.keyPress(KeyEvent.VK_TAB);
                robot.delay(100);
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.delay(1000);
            for (int i = 0; i < path.length(); i++) {
                typeCharacter(robot, path.charAt(i));
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.delay(1000);
            robot.delay(1000);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Type a specific character using java Robot class.
     */
    public static void typeCharacter(Robot robot, char c) {
        try {
            if (c == ':') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (c == '\\') {
                robot.keyPress(KeyEvent.VK_BACK_SLASH);
                robot.keyRelease(KeyEvent.VK_BACK_SLASH);
            } else if (c == '_') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else {
                boolean upperCase = Character.isUpperCase(c);
                String variableName = "VK_" + Character.toUpperCase(c);
                System.out.println(variableName);
                Class<KeyEvent> clazz = KeyEvent.class;
                Field field = clazz.getField(variableName);
                int keyCode = field.getInt(null);
                if (upperCase) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                if (upperCase) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Navigate to the link by IE browser (with credentials by usr/pwd and title
     * of this site)
     */


    /**
     * Navigate to the link by IE browser (with credentials by usr/pwd and title
     * of this site)
     */

    /**
     * Authenticate in the separated login form. Assume that we are standing at
     * login page
     */
    public WebDriver authInSeparatedLogin(WebDriver driver, String userElement, String pwElement,
            String userValue, String pwValue, Long miliseconds) {
        try {
            WebElement element = driver.findElement(By.id(userElement));
            element.clear();
            element.sendKeys(userValue);

            element = driver.findElement(By.id(pwElement));
            element.clear();
            element.sendKeys(pwValue);
            element.sendKeys(Keys.RETURN);
            Thread.sleep(miliseconds);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    /**
     * Navigate to the link by IE browser via hub (with credentials by usr/pwd
     * and title of this site)
     */


    public WebDriver IERemote(String port, String driverFile) throws MalformedURLException {
        File file = new File(driverFile);
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        WebDriver driver;
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
        //capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        //capability.setBrowserName("iexplorer");
        capability.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
        capability.setPlatform(org.openqa.selenium.Platform.ANY);
        driver = new RemoteWebDriver(new URL("http://localhost:" + port + "/wd/hub"), capability);
        return driver;
    }
}
