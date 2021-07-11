package ai.autonomous.util;

//import ghn.Gido.util.ANSIColor;
import org.junit.Assert;
import java.util.Date;

/**
 * Created by Son Vu
 */

public class Log {

    private static String reportPrefix = "_";

    public static void highlight(String content) {
        content = ANSIColor.GREEN + content + ANSIColor.RESET;
        printLog(content);
    }

    public static void error(String content) {
        //Redmine.newIssue(content);
        content = ANSIColor.RED + content + ANSIColor.RESET;
        printLog(content);
    }

    public static void errorAndStop(String content) {
        error(content + " - STOP APP");
        Assert.fail(content + " - STOP APP");
    }

    public static void debug(String content) {
        content = ANSIColor.YELLOW + content + ANSIColor.RESET;
        printLog(content);
    }

    public static void info(String content) {
        printLog(content);
    }


    private static void printLog(String content) {
        Date currentDate = new Date();

        content = currentDate.toString() + " | Thread : "
                + Thread.currentThread().getId() + " | Autonomous Auto : " + content;
        content = unescapeString(content);
        System.out.println(content);
    }

    public static String unescapeString(String st) {

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        .charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt(
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                        + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

}
