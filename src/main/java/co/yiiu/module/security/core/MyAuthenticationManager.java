package co.yiiu.module.security.core;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya at 2018/4/4
 */
@Component
public class MyAuthenticationManager implements AuthenticationManager {
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return authentication;
  }
}
