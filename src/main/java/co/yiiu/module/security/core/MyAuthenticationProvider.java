package co.yiiu.module.security.core;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by tomoya at 5/21/18
 */
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    String username = token.getName();
    UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(username);
    // 验证密码是否正确
    if (!new BCryptPasswordEncoder().matches((CharSequence) token.getCredentials(), userDetails.getPassword())) {
      throw new AuthenticationServiceException("用户名或密码错误");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.equals(authentication);
  }
}
