package autonomous.automation.web.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This component is to verify all links, resources of a page is dead or not.
 * Once a page is verified, the framework will continue verify the sub page and
 * so on.
 * <p/>
 */
public class VerifyDeadLinks {

    WebDriver driver;

    Map<String, WebLink> urlMap = new LinkedHashMap<>();
    public static String vdlPassMsg = "";
    public static String vdlFailMsg = "";
    private static String contentTypePattern;
    private static int depth;
    private static String currentLink = ""; //current Link should get from urlMap, not from driver.getCurentLink

    /**
     * Crawling all links of the current page check if any dead links/resources
     * Log the result into 2 result files. Usage: Option1: VERIFY_DeadLinks arg1
     * arg2 - arg1 = depth - arg2 = pattern of type(Ex: imagine: all image
     * resource, jpeg: just image/jpeg resource) Result output includes all
     * link(tag name = "a") and specific resource scope Ex: VERIFY_DeadLinks 2
     * image Option2: VERIFY_DeadLinks arg1 - arg1 = depth Ex: VERIFY_DeadLinks
     * 2 Option3: VERIFY_DeadLinks - recursive all sub page List of content type
     * can be referenced as the link below:
     * http://webdesign.about.com/od/multimedia/a/mime-types-by-content-type.htm
     */
    public void perform(String[] args) {

        if (args.length == 0) {
            this.depth = 9999; //nearly all sub page
            this.contentTypePattern = "*";
        }
        if (args.length == 1) {
            this.depth = Integer.parseInt(args[0]);
            this.contentTypePattern = "*";
        }
        if (args.length == 2) {
            this.depth = Integer.parseInt(args[0]);
            this.contentTypePattern = "(.*)" + args[1].toLowerCase() + "(.*)";
        }


        WebLink generalLink = new WebLink(null, driver.getCurrentUrl(), "link", 0, true, true);
        System.out.println("General object: " + urlMap.get(driver.getCurrentUrl()));
        urlMap.put(generalLink.getUrl(), generalLink);
        this.currentLink = generalLink.getUrl();
        crawl(generalLink.getUrl());
        long endTime = System.currentTimeMillis();
        DecimalFormat df = new DecimalFormat("#,###,##0");

    }

    /**
     * Check all resources are dead or not - css - all tags contain src
     * attribute - all links	*
     */
    public void crawl(String url) {
        System.out.println("Current URL: " + this.currentLink);
        WebLink wl = urlMap.get(url);
        int currentDepth = wl.getLevel();
        System.out.println("Parent's depth: " + currentDepth);
        if (currentDepth < this.depth) {
            checkCSS();
            checkSource();
            checkLink();
            String strNextLink = fetchNextUrl();
            if (strNextLink != "") {
                this.currentLink = strNextLink;
                driver.get(strNextLink);

                crawl(strNextLink);
            }
        }
    }

    /**
     * Return true if contentTye matches with contentType pattern
     */
    private boolean isMatchWithContentTypePattern(String contentTye) {
        return contentTye.matches(this.contentTypePattern);
    }

    /**
     * Check if all links in the web page are dead or not. and report it. How it
     * works: - Use selenium to find all A tag and get their href
     */
    public void checkLink() {
        try {
            List<WebElement> lsA = driver.findElements(By.tagName("a"));
            for (WebElement we : lsA) {
                try {
                    String url = we.getAttribute("href");
//                    System.out.println(url);
                    if (urlMap.containsKey(url)) {
                        continue;
                    }
                    checkLinkAlive(url);
                } catch (Exception ex) {
                    System.out.println("***** Problem: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println("***** Problem: " + ex.getMessage());
        }
    }

    public List<String> getListOfLinks() {
        List<WebElement> lsA = driver.findElements(By.tagName("a"));
        List<String> links = new ArrayList<>();
        for (WebElement we : lsA) {
            String url = we.getAttribute("href");
            links.add(url);
        }
        return links;
    }

    /**
     * Check if all .css files exists and log to the reports How it works: -
     * Search all link tags in the current page and get href attribute
     */
    void checkCSS() {

        try {
            List<WebElement> lsA = driver.findElements(By.tagName("link"));
            for (WebElement we : lsA) {
                try {
                    String url = we.getAttribute("href");

                    checkResourceAlive(url);
                } catch (Exception ex) {
                    System.out.println("***** Problem: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println("***** Problem: " + ex.getMessage());
        }

    }

    void checkSource() {
        try {
            List<WebElement> lsA = driver.findElements(By.xpath("//*[@src]"));
            for (WebElement we : lsA) {
                try {
                    String url = we.getAttribute("src");

                    checkResourceAlive(url);
                } catch (Exception ex) {
                    System.out.println("***** Problem: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println("***** Problem: " + ex.getMessage());
        }
    }

    /**
     * Check a link is alive or not How it works: - use the net package of java
     * to get the code of an url.
     *
     * @param url: a url to check.
     * @return - true: alive - false: not alive
     */
    boolean checkLinkAlive(String url) {
        boolean bRes = false;
        try {
            URL u = new URL(url);
            HttpURLConnection urlcon = (HttpURLConnection) u.openConnection();
            urlcon.connect();
            int code = urlcon.getResponseCode();

            report(url, code);
            if (code < 400) {
                addUrlIfNotExist(url);
            }

        } catch (IOException e) {
            reportInvalidUrl(url);
        }
        return bRes;
    }

    /**
     * Check resource alive
     *
     * @param url url of the resource
     * @return true: alive, false not alive
     */
    boolean checkResourceAlive(String url) {
        boolean bRes = false;
        try {
            URL u = new URL(url);
            HttpURLConnection urlcon = (HttpURLConnection) u.openConnection();
            urlcon.connect();
            int code = urlcon.getResponseCode();
            String contentType = urlcon.getContentType().toLowerCase();

            if (contentTypePattern.contentEquals("*") || isMatchWithContentTypePattern(contentType)) {
                System.out.println("Matched content: " + contentType);
                report(url, code);
            }

        } catch (IOException e) {
            reportInvalidUrl(url);
        }
        return bRes;
    }

    /**
     * Report an url is invalid.
     *
     * @param url url to report
     */
    void reportInvalidUrl(String url) {
        String msg = String.format("Invalid url: '%s'", url);
    }

    /**
     * Report an url with its code
     *
     * @param url url to report
     * @param code code of the url
     */
    void report(String url, int code) throws IOException {
        String msg = "";
        if (code >= 200 && code < 400) {
            msg = String.format("[PASS] Status: '%d'. Src Page: '%s'. Url: '%s'. ", code, driver.getCurrentUrl(), url);
            vdlPassMsg = vdlPassMsg + "\r\n" + msg;
        }
        if (code >= 400) {
            msg = String.format("[FAIL] Status: '%d'. Src Page: '%s'. Url: '%s'. ", code, driver.getCurrentUrl(), url);
            vdlFailMsg = vdlFailMsg + "\r\n" + msg;
        }

    }

    /**
     * Add an url to the url map if it doesn't exist in the map
     *
     * @param url url to add
     */
    void addUrlIfNotExist(String url) {
        if (!url.contains("logout") && !urlMap.containsKey(url)) {
            int depth = urlMap.get(this.currentLink).getLevel() + 1;
            System.out.println("Add new Url: " + url);
            System.out.println("Child's depth:" + depth);
            WebLink wl = new WebLink(driver.getCurrentUrl(), url, "link", depth, false, true);
            urlMap.put(url, wl);
        }

        System.out.println(String.format("Size of urls: {%d}", urlMap.keySet().size()));
    }

    /**
     * Fetch the next valid url from the map
     *
     * @return a ur l
     */
    String fetchNextUrl() {
        for (String url : urlMap.keySet()) {

            if (!urlMap.get(url).getStatus()) {
                WebLink wl = urlMap.get(url);
                wl.setStatus(true);
                urlMap.put(url, wl);
                return url;
            }
        }
        return "";
    }

}

class WebLink {

    private String parent;
    private String url;
    private String type;
    private int level;
    private boolean status;
    private boolean result;


    public void show() {
        System.out.println("--------------------------------------------");
        System.out.println("parent: " + parent);
        System.out.println("Url: " + url);
        System.out.println("type: " + type);
        System.out.println("level: " + level);
        System.out.println("status: " + status);
        System.out.println("result: " + result);

        System.out.println("--------------------------------------------");
    }

    public WebLink(String parent, String url, String type, int level, boolean status, boolean result) {
        this.parent = parent;
        this.url = url;
        this.type = type;
        this.status = status;
        this.level = level;
        this.result = result;
    }

    public String getParent() {
        return this.parent;
    }

    public String getUrl() {
        return this.url;
    }

    public String getType() {
        return this.type;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean getResult() {
        return this.result;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
