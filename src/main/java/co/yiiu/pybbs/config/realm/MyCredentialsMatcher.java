package co.yiiu.pybbs.config.realm;

import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class MyCredentialsMatcher implements CredentialsMatcher {

  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    // 大坑！！！！！！！！！！！！！！！！！！！
    // 明明token跟info两个对象的里的Credentials类型都是Object，断点看到的类型都是 char[]
    // 但是！！！！！ token里转成String要先强转成 char[]
    // 而info里取Credentials就可以直接使用 String.valueOf() 转成String
    // 醉了。。
    String rawPassword = String.valueOf((char[]) token.getCredentials());
    String encodedPassword = String.valueOf(info.getCredentials());
    return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
  }
}
