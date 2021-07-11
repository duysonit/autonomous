/*
 * Zyllem
 */
package autonomous.automation.web.util;

import java.net.MalformedURLException;
import java.net.URL;


public class URLUtil {

    public static String getBaseURL(String fullPath) throws MalformedURLException {
        URL url = new URL(fullPath);
        String baseUrl = url.getProtocol() + "://" + url.getHost();
        return baseUrl;
    }
    
    public static void main(String[] args) throws Exception{
        System.out.println(getBaseURL("http://stackoverflow.com/questions/4492944/extract-base-url-from-full-url"));
    }
}
