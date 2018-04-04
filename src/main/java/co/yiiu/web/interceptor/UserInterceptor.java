package co.yiiu.web.interceptor;

import co.yiiu.core.base.BaseEntity;
import co.yiiu.module.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya at 2018/3/15
 */
@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

  @Autowired
  private BaseEntity baseEntity;

  /**
   * 如果session里没有用户信息，那么取cookie里的token，然后去数据库里查用户信息，再将用户信息存在session里
   * @param request
   * @param response
   * @param o
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
    User user = baseEntity.getUser();
    Assert.notNull(user, "请先登录，点击去<a href='/login'>登录</a>");
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

  }
}
