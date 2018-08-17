package co.yiiu.module.security.core;

import co.yiiu.config.properties.SiteConfig;
import co.yiiu.module.security.pojo.AdminUser;
import co.yiiu.module.security.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@Component
public class MyCustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private AdminUserService adminUserService;
  @Autowired
  private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

  @PostConstruct
  public void init() {
    SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setDefaultTargetUrl("/admin/index");

    setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/adminlogin", "POST"));
    setAuthenticationSuccessHandler(successHandler);
    setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/adminlogin?error"));
    setRememberMeServices(persistentTokenBasedRememberMeServices);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    // 只接受POST方式传递的数据
    if (!"POST".equals(request.getMethod()))
      throw new AuthenticationServiceException("不支持非POST方式的请求!");
    // 验证码验证
    String requestCaptcha = request.getParameter("code");
    String genCaptcha = (String) request.getSession().getAttribute("index_code");
    if (StringUtils.isEmpty(requestCaptcha))
      throw new AuthenticationServiceException("验证码不能为空!");
    if (!genCaptcha.toLowerCase().equals(requestCaptcha.toLowerCase())) {
      throw new AuthenticationServiceException("验证码错误!");
    }

    // 判断登陆次数及上限时间
    String username = obtainUsername(request);
    AdminUser adminUser = adminUserService.findByUsername(username);
    if (adminUser == null) {
      throw new AuthenticationServiceException("用户不存在!");
    } else {
      if (adminUser.getAttempts() >= siteConfig.getAttempts()) {
        Calendar dateOne = Calendar.getInstance(), dateTwo = Calendar.getInstance();
        dateOne.setTime(new Date());
        dateTwo.setTime(adminUser.getAttemptsTime());
        long timeOne = dateOne.getTimeInMillis();
        long timeTwo = dateTwo.getTimeInMillis();
        long minute = (timeOne - timeTwo) / (1000 * 60);// 转化minute
        if (minute < siteConfig.getAttemptsWaitTime()) {
          throw new AuthenticationServiceException(
              "密码错误超过" + siteConfig.getAttempts() + "次，账号已被锁定" + siteConfig.getAttemptsWaitTime() + "分钟");
        } else {
          adminUser.setAttempts(0);
        }
      }
    }
    return super.attemptAuthentication(request, response);
  }

  @Override
  @Autowired
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }
}
