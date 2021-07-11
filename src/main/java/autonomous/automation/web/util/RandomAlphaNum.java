/*
 * Liquid Pay
 */
package autonomous.automation.web.util;

import static java.lang.Math.*;
import static org.apache.commons.lang3.StringUtils.leftPad;


public class RandomAlphaNum {

    public static String gen(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = length; i > 0; i -= 12) {
            int n = Math.min(12, abs(i));
            sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
        }
        return sb.toString();
    }
}
