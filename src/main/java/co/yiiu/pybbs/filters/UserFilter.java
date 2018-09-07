package co.yiiu.pybbs.filters;

import co.yiiu.pybbs.conf.properties.SiteConfig;
import co.yiiu.pybbs.exceptions.ApiAssert;
import co.yiiu.pybbs.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya at 2018/9/5
 */
@Component
public class UserFilter implements HandlerInterceptor {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private SiteConfig siteConfig;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String authToken = (String) request.getAttribute("authToken");
    String username = jwtTokenUtil.getUsernameFromToken(authToken);
    ApiAssert.notTrue(siteConfig.getBan().contains(username), "您的帐号已被封");
    return true;
  }
}
