package co.yiiu.web.front;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.util.CookieHelper;
import co.yiiu.module.es.model.TopicIndex;
import co.yiiu.module.es.service.TopicSearchService;
import co.yiiu.module.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
public class IndexController extends BaseController {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private TagService tagService;
  @Autowired
  private TopicSearchService topicSearchService;

  /**
   * 首页
   *
   * @return
   */
  @GetMapping("/")
  public String index(String tab, Integer p, Model model) {
    model.addAttribute("p", p);
    model.addAttribute("tab", tab);
    return "front/index";
  }

  /**
   * 搜索
   * @param keyword 关键字
   * @param pageNo
   * @param model
   * @return
   */
  @GetMapping("/search")
  public String search(String keyword, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    Assert.notNull(keyword, "请输入关键词");
    Page<TopicIndex> page = topicSearchService.query(keyword, pageNo, siteConfig.getPageSize());
    model.addAttribute("page", page);
    model.addAttribute("keyword", keyword);
    return "front/search";
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

  /**
   * 进入注册页面
   *
   * @return
   */
  @GetMapping("/register")
  public String toRegister() {
    return "front/register";
  }

  // 登出
  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    CookieHelper.clearCookieByName(request, response, siteConfig.getCookie().getUserName(),
        siteConfig.getCookie().getDomain(), "/");
    return redirect("/");
  }

}
