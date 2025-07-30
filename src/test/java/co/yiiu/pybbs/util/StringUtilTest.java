package co.yiiu.pybbs.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya at 2019/5/24
 */
public class StringUtilTest {

    @Test
    public void check() {
        String url = "https://www.97560.com/topic/538825475611426816/?aa=dasd&asd=123123123";
        String atRegex = "/topic/(\\d+)";
        Pattern regex = Pattern.compile(atRegex);
        Matcher regexMatcher = regex.matcher(url);
        while (regexMatcher.find()) {
            System.out.println(regexMatcher.group(1));
        }
    }

    @Test
    public void test() {
        String s = "Abcdefght123456";
        System.out.println(StringUtil.check(s, StringUtil.PASSWORDREGEX));
    }

    public void convert(String s, int numRows) {
        int x = s.length() / numRows + numRows;
        char[][] c = new char[x][numRows];
        char[] schar = s.toCharArray();
        int offset = 0;
        int index = s.length() - 1;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if (offset > 0 && offset != j) {
                    continue;
                }
                c[i][j] = schar[index];
                index++;
            }
            offset++;
            if (offset == numRows - 1) offset = 0;
        }
    }
}
