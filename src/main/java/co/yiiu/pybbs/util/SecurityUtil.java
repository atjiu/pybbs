package co.yiiu.pybbs.util;


import org.apache.commons.text.StringEscapeUtils;

public class SecurityUtil {

    public static String sanitizeInput(String input) {
        return StringEscapeUtils.escapeHtml3(StringEscapeUtils.escapeHtml4(StringEscapeUtils.escapeEcmaScript(input)));
    }
}
