package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.config.service.ElasticSearchService;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.service.TagService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.CookieUtil;
import co.yiiu.pybbs.util.StringUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

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
  @Autowired
  private ElasticSearchService elasticSearchService;

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
    cookieUtil.clearCookie(systemConfigService.selectAllConfig().get("cookie_name").toString());
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

  @GetMapping("/search")
  public String search(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam String keyword, Model model) {
    Assert.isTrue(systemConfigService.selectAllConfig().get("search").toString().equals("1"), "网站没有启动搜索功能，联系站长问问看");
    if (StringUtils.isEmpty(keyword)) {
      model.addAttribute("page", new Page<>());
    } else {
      Integer pageSize = Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString());
      Page<Map<String, Object>> page = elasticSearchService.searchDocument(pageNo, pageSize, keyword, "title", "content");
      model.addAttribute("page", page);
      model.addAttribute("keyword", keyword);
    }
    return "front/search";
  }

  // 切换语言
  @GetMapping("changeLanguage")
  public String changeLanguage(String lang, HttpSession session, HttpServletRequest request) {
    String referer = request.getHeader("referer");
    if ("zh".equals(lang)) {
      session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.SIMPLIFIED_CHINESE);
    } else if ("en".equals(lang)) {
      session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.US);
    }
    return StringUtils.isEmpty(referer) ? redirect("/") : redirect(referer);
  }
}
