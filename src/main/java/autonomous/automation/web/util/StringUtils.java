/*
 * Liquid Pay
 */
package autonomous.automation.web.util;

import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {

    public static final String IF_ELSE_STATEMENT = "IF_ELSE_STATEMENT";
    public static final String FOREACH_STATEMENT = "FOREACH_STATEMENT";

    public static String escapeSpecialRegexChars(String str) {
        java.util.regex.Pattern SPECIAL_REGEX_CHARS = java.util.regex.Pattern.compile("[{}()<>\\[\\].+*?^$\\\\|]");
        return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
    }

    public static String escapeIfElse(String str) {
        Pattern pattern = Pattern.compile("(@if.*\\{.*})");
        String resultString = pattern.matcher(str).replaceAll(IF_ELSE_STATEMENT);

        return resultString;
    }

    public static String escapeForeach(String str) {
        Pattern pattern = Pattern.compile("(@foreach.*\\{.*})");
        String resultString = pattern.matcher(str).replaceAll(FOREACH_STATEMENT);

        return resultString;
    }

    public static String getIncrementedString(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            sb.append(++c);
        }
        return sb.toString();
    }

    public static String printList(List<String> list) {
        String str = "";
        for (String s : list) {
            if (str.isEmpty()) {
                str = s;
            } else {
                str = str + " -> " + s;
            }
        }
        return str;
    }
}
