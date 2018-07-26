package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.util.CookieHelper;
import co.yiiu.module.es.service.TagSearchService;
import co.yiiu.module.es.service.TopicSearchService;
import co.yiiu.module.security.service.AdminUserService;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController extends BaseController {

  @Autowired
  private AdminUserService adminUserService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private UserService userService;
  @Autowired
  private TopicSearchService topicSearchService;
  @Autowired
  private TagSearchService tagSearchService;

  @GetMapping("/index")
  public String index() {
    return "admin/index";
  }

  @GetMapping("/clear")
  @ResponseBody
  public Result clear(Integer type) {
    if (type == 1) {
      userService.deleteAllRedisUser();
    } else if (type == 2) {
      adminUserService.deleteAllRedisAdminUser();
    }
    return Result.success();
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    CookieHelper.clearCookieByName(request, response, siteConfig.getCookie().getAdminUserName(),
        siteConfig.getCookie().getDomain(), "/admin/");
    return redirect("/admin/login");
  }

  @GetMapping("/indexedTopic")
  @ResponseBody
  public Result topicIndexed() {
    topicSearchService.indexedAll();
    return Result.success();
  }

  @GetMapping("/indexedTag")
  @ResponseBody
  public Result indexedTag() {
    tagSearchService.indexedAll();
    return Result.success();
  }
}
