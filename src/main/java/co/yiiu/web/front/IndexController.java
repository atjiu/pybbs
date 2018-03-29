package co.yiiu.web.front;

import co.yiiu.config.LogEventConfig;
import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.CookieHelper;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.core.util.StrUtil;
import co.yiiu.core.util.identicon.Identicon;
import co.yiiu.core.util.security.Base64Helper;
import co.yiiu.core.util.security.crypto.BCryptPasswordEncoder;
import co.yiiu.module.code.model.CodeEnum;
import co.yiiu.module.code.service.CodeService;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.tag.service.TagService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
public class IndexController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private Identicon identicon;
  @Autowired
  private CodeService codeService;
  @Autowired
  FreemarkerUtil freemarkerUtil;
  @Autowired
  LogEventConfig logEventConfig;
  @Autowired
  LogService logService;
  @Autowired
  private TagService tagService;

  /**
   * 首页
   *
   * @return
   */
  @GetMapping("/")
  public String index(String tab, Integer p, Model model) {
    model.addAttribute("p", p);
    model.addAttribute("tab", tab);
    System.out.println(siteConfig.getMail().getRegister().get("subject"));
    return "front/index";
  }

  @GetMapping("/tags")
  public String tags(@RequestParam(defaultValue = "1") Integer p, Model model) {
    model.addAttribute("page", tagService.page(p, siteConfig.getPageSize()));
    return "front/tag/list";
  }

  /**
   * top 100 user log
   *
   * @return
   */
  @GetMapping("/top100")
  public String top100() {
    return "front/top100";
  }

  /**
   * 进入登录页
   *
   * @return
   */
  @GetMapping("/login")
  public String toLogin(String s, Model model) {
    model.addAttribute("s", s);
    return "front/login";
  }

  // 用户登录
  @PostMapping("/login")
  @ResponseBody
  public Result login(String username, String password, Boolean rememberMe, HttpServletResponse response, HttpSession session) {
    ApiAssert.notEmpty(username, "用户名不能为空");
    ApiAssert.notEmpty(password, "密码不能为空");

    User user = userService.findByUsername(username);
    ApiAssert.notNull(user, "用户不存在");
    ApiAssert.notTrue(user.getBlock(), "用户已被禁");

    ApiAssert.isTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()), "密码不正确");

    // 把用户信息写入session
    session.setAttribute("user", user);
    if(rememberMe) {
      // 把用户信息写入cookie
      CookieHelper.addCookie(
          response,
          siteConfig.getCookie().getDomain(),
          "/",
          siteConfig.getCookie().getUserName(),
          Base64Helper.encode(user.getToken().getBytes()),
          siteConfig.getCookie().getUserMaxAge() * 24 * 60 * 60,
          true,
          false
      );
    }

    return Result.success();
  }

  /**
   * 进入注册页面
   *
   * @return
   */
  @GetMapping("/register")
  public String toRegister() {
    return "front/register";
  }

  /**
   * 注册验证
   *
   * @param username
   * @param password
   * @return
   */
  @PostMapping("/register")
  @ResponseBody
  public Result register(String username, String password, String email, String emailCode, String code,
                         HttpSession session) {
    String genCaptcha = (String) session.getAttribute("index_code");
    ApiAssert.notEmpty(code, "验证码不能为空");
    ApiAssert.notEmpty(username, "用户名不能为空");
    ApiAssert.notEmpty(password, "密码不能为空");
    ApiAssert.notEmpty(email, "邮箱不能为空");

    ApiAssert.isTrue(genCaptcha.toLowerCase().equals(code.toLowerCase()), "验证码错误");
    ApiAssert.isTrue(StrUtil.check(username, StrUtil.userNameCheck), "用户名不合法");

    User user = userService.findByUsername(username);
    ApiAssert.isNull(user, "用户名已经被注册");

    User user_email = userService.findByEmail(email);
    ApiAssert.isNull(user_email, "邮箱已经被使用");

    int validateResult = codeService.validateCode(email, emailCode, CodeEnum.EMAIL);
    ApiAssert.notTrue(validateResult == 1, "邮箱验证码不正确");
    ApiAssert.notTrue(validateResult == 2, "邮箱验证码已过期");
    ApiAssert.notTrue(validateResult == 3, "邮箱验证码已经被使用");

    // generator avatar
    String avatar = identicon.generator(username);

    // 创建用户
    userService.createUser(username, password, email, avatar, null, null);

    return Result.success();
  }

  // 登出
  @GetMapping("/logout")
  public String logout(HttpServletResponse response, HttpSession session) {
    session.removeAttribute("user");
    CookieHelper.clearCookieByName(response, siteConfig.getCookie().getUserName());
    return redirect("/");
  }

}
