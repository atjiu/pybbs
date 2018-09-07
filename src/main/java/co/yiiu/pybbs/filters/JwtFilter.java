package co.yiiu.pybbs.filters;

import co.yiiu.pybbs.conf.properties.JwtConfig;
import co.yiiu.pybbs.exceptions.ApiAssert;
import co.yiiu.pybbs.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya at 2018/9/3
 */
@Component
public class JwtFilter implements HandlerInterceptor {

  @Autowired
  private JwtConfig jwtConfig;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String authHeader = request.getHeader(jwtConfig.getHeader());
    ApiAssert.notTrue(authHeader == null || !authHeader.startsWith(jwtConfig.getTokenHead()), 202, "无效Token");
    String authToken = authHeader.substring(jwtConfig.getTokenHead().length()); // The part after "Bearer "
    ApiAssert.isTrue(jwtTokenUtil.validateToken(authToken), 202, "Token不合法或已过期");
    return true;
  }
}
