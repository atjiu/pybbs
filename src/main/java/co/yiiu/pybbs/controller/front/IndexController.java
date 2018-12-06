package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.service.TagService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.CookieUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
public class IndexController extends BaseController {

  private Logger log = LoggerFactory.getLogger(IndexController.class);

  @Autowired
  private CookieUtil cookieUtil;
  @Autowired
  private SystemConfigService systemConfigService;
  @Autowired
  private UserService userService;
  @Autowired
  private TagService tagService;

  // 首页
  @GetMapping({"/", "/index", "/index.html"})
  public String index(@RequestParam(defaultValue = "all") String tab, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    model.addAttribute("tab", tab);
    model.addAttribute("pageNo", pageNo);
    return "front/index";
  }

  @GetMapping("/top100")
  public String top100() {
    return "front/top100";
  }

  @GetMapping("/settings")
  public String settings(HttpSession session, Model model) {
    // 再查一遍，保证数据的最新
    User user = (User) session.getAttribute("_user");
    user = userService.selectById(user.getId());
    model.addAttribute("user", user);
    return "front/user/settings";
  }

  @GetMapping("/tags")
  public String tags(@RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    IPage<Tag> page = tagService.selectAll(pageNo, null);
    model.addAttribute("page", page);
    return "front/tag/tags";
  }

  // 登出
  @GetMapping("/logout")
  public String logout(HttpSession session) {
    // 清除session里的用户信息
    session.removeAttribute("_user");
    // 清除cookie里的用户token
    cookieUtil.clearCookie(systemConfigService.selectAllConfig().get("cookie.name").toString());
    return redirect("/");
  }

  // 登录后台
  @GetMapping("/adminlogin")
  public String adminlogin() {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) return redirect("/admin/index");
    return "admin/login";
  }

  // 处理后台登录逻辑
  @PostMapping("/adminlogin")
  public String adminLogin(String username, String password, String code, HttpSession session,
                           @RequestParam(defaultValue = "0") Boolean rememberMe,
                           RedirectAttributes redirectAttributes) {
    String index_code = (String) session.getAttribute("index_code");
    if (index_code == null || StringUtils.isEmpty(code) || !index_code.equalsIgnoreCase(code)) {
      redirectAttributes.addFlashAttribute("error", "验证码不正确");
    } else {
      try {
        // 添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
          UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
          //进行验证，这里可以捕获异常，然后返回对应信息
          subject.login(token);
        }
      } catch (AuthenticationException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        redirectAttributes.addFlashAttribute("error", "用户名或密码错误");
        redirectAttributes.addFlashAttribute("username", username);
        return redirect("/adminlogin");
      }
    }
    return redirect("/admin/index");
  }
}
