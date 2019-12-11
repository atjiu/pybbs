package co.yiiu.pybbs.util;

import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import org.junit.Test;

/**
 * Created by tomoya at 2019/5/24
 */
public class StringUtilTest {

    @Test
    public void check() {
        String username = "admin@dhajksd.ahsd.xyz";
        System.out.println(StringUtil.check(username, StringUtil.USERNAMEREGEX));

        System.out.println(new BCryptPasswordEncoder().encode("123123"));
    }
}
