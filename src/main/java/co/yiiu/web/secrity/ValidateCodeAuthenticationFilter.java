package co.yiiu.web.secrity;

import co.yiiu.config.SiteConfig;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@Component
public class ValidateCodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private UserService userService;

  @Autowired
  private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

  @PostConstruct
  public void init() {
    String failureUrl = "/login?error";
    setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
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
    User user = userService.findByUsername(username);
    if (user == null) {
      throw new AuthenticationServiceException("用户名或密码错误!");
    } else {
      if (user.getAttempts() >= siteConfig.getAttempts()) {
        Calendar dateOne = Calendar.getInstance(), dateTwo = Calendar.getInstance();
        dateOne.setTime(new Date());
        dateTwo.setTime(user.getAttemptsTime());
        long timeOne = dateOne.getTimeInMillis();
        long timeTwo = dateTwo.getTimeInMillis();
        long minute = (timeOne - timeTwo) / (1000 * 60);// 转化minute
        if (minute < siteConfig.getAttemptsWaitTime()) {
          throw new AuthenticationServiceException(
              "密码错误超过" + siteConfig.getAttempts() + "次，账号已被锁定" + siteConfig.getAttemptsWaitTime() + "分钟");
        } else {
          user.setAttempts(0);
        }
      }
    }
    // 验证密码是否正确
    if (new BCryptPasswordEncoder().matches(obtainPassword(request), user.getPassword())) {
      user.setAttempts(0);
      user.setAttemptsTime(new Date());
      userService.save(user);
    } else {
      if (user.getAttempts() < siteConfig.getAttempts()) {
        user.setAttempts(user.getAttempts() + 1);
      }
      user.setAttemptsTime(new Date());
      userService.save(user);
      throw new AuthenticationServiceException("用户名或密码错误");
    }
    return super.attemptAuthentication(request, response);
  }

  @Override
  @Autowired
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }
}
