package co.yiiu.pybbs.util;

import org.junit.Test;

/**
 * Created by tomoya at 2019/5/24
 */
public class StringUtilTest {

  @Test
  public void check() {
    String username = "admin@dhajksd.ahsd.xyz";
    System.out.println(StringUtil.check(username, StringUtil.USERNAMEREGEX));
  }
}
