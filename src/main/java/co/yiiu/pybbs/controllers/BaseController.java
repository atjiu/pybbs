package co.yiiu.pybbs.controllers;

import co.yiiu.pybbs.conf.properties.JwtConfig;
import co.yiiu.pybbs.exceptions.ApiAssert;
import co.yiiu.pybbs.models.AccessToken;
import co.yiiu.pybbs.models.User;
import co.yiiu.pybbs.services.AccessTokenService;
import co.yiiu.pybbs.services.UserService;
import co.yiiu.pybbs.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tomoya at 2018/9/3
 */
public class BaseController {

  @Autowired
  private JwtConfig jwtConfig;
  @Autowired
  private UserService userService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private AccessTokenService accessTokenService;

  private String getToken() {
    HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    String authHeader = request.getHeader(jwtConfig.getHeader());
    String token = authHeader.substring(jwtConfig.getTokenHead().length());
    AccessToken accessToken = accessTokenService.findByToken(token);
    ApiAssert.notNull(accessToken, 202, "您的Token已无效，请重新登录获取");
    return token;
  }

  protected User getUserForTopicDetail() {
    HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    String authHeader = request.getHeader(jwtConfig.getHeader());
    String token = authHeader.substring(jwtConfig.getTokenHead().length());
    AccessToken accessToken = accessTokenService.findByToken(token);
    if (accessToken != null) {
      return userService.findById(accessToken.getUserId());
    }
    return null;
  }

  protected User getUser() {
    return userService.findByUsername(jwtTokenUtil.getUsernameFromToken(this.getToken()));
  }

}
