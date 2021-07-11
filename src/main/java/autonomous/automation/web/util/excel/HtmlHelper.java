/*
 * Liquid Pay
 */


package autonomous.automation.web.util.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Formatter;


public interface HtmlHelper {
    void colorStyles(CellStyle style, Formatter out);
}
