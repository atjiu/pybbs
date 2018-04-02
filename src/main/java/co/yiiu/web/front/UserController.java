package co.yiiu.web.front;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.exception.ApiException;
import co.yiiu.core.util.identicon.Identicon;
import co.yiiu.core.util.security.Base64Helper;
import co.yiiu.core.util.security.crypto.BCryptPasswordEncoder;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private Identicon identicon;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private LogService logService;

  /**
   * 个人资料
   *
   * @param username
   * @param model
   * @return
   */
  @GetMapping("/{username}")
  public String profile(@PathVariable String username, Model model) {
    model.addAttribute("username", username);
    return "front/user/info";
  }

  /**
   * 用户发布的所有话题
   *
   * @param username
   * @return
   */
  @GetMapping("/{username}/topics")
  public String topics(@PathVariable String username, Integer p, Model model) {
    model.addAttribute("username", username);
    model.addAttribute("p", p);
    return "front/user/topics";
  }

  /**
   * 用户发布的所有评论
   *
   * @param username
   * @return
   */
  @GetMapping("/{username}/comments")
  public String comments(@PathVariable String username, Integer p, Model model) {
    model.addAttribute("username", username);
    model.addAttribute("p", p);
    return "front/user/comments";
  }

  /**
   * 用户收藏的所有话题
   *
   * @param username
   * @return
   */
  @GetMapping("/{username}/collects")
  public String collects(@PathVariable String username, Integer p, Model model) {
    model.addAttribute("username", username);
    model.addAttribute("p", p);
    return "front/user/collects";
  }

  /**
   * 进入用户个人设置页面
   *
   * @param model
   * @return
   */
  @GetMapping("/setting/profile")
  public String setting(Model model) {
    model.addAttribute("user", getUser());
    return "front/user/setting/profile";
  }

  /**
   * 更新用户的个人设置
   *
   * @param email
   * @param url
   * @param bio
   * @param response
   * @return
   */
  @PostMapping("/setting/profile")
  public String updateUserInfo(String email, String url, String bio, HttpServletResponse response,
                               @RequestParam(defaultValue = "false") Boolean commentEmail,
                               @RequestParam(defaultValue = "false") Boolean replyEmail) throws Exception {
    User user = getUser();
    if (user.getBlock())
      throw new Exception("你的帐户已经被禁用，不能进行此项操作");
    user.setEmail(email);
    if (bio != null && bio.trim().length() > 0) user.setBio(Jsoup.clean(bio, Whitelist.none()));
    user.setCommentEmail(commentEmail);
    user.setReplyEmail(replyEmail);
    user.setUrl(url);
    userService.save(user);
    return redirect(response, "/user/" + user.getUsername());
  }

  /**
   * 修改头像
   *
   * @param model
   * @return
   */
  @GetMapping("/setting/changeAvatar")
  public String changeAvatar(Model model) {
    model.addAttribute("user", getUser());
    return "front/user/setting/changeAvatar";
  }

  /**
   * 保存头像
   *
   * @param avatar
   * @return
   * @throws ApiException
   */
  @PostMapping("setting/changeAvatar")
  @ResponseBody
  public Result changeAvatar(String avatar) throws ApiException, IOException {
    if (StringUtils.isEmpty(avatar)) throw new ApiException("头像不能为空");
    String _avatar = avatar.substring(avatar.indexOf(",") + 1, avatar.length());
    User user = getUser();
    byte[] bytes;
    try {
      bytes = Base64Helper.decode(_avatar);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ApiException("头像格式不正确");
    }
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    BufferedImage bufferedImage = ImageIO.read(bais);
    String __avatar = identicon.saveFile(user.getUsername(), bufferedImage);
    user.setAvatar(__avatar);
    userService.save(user);
    bais.close();
    return Result.success();
  }

  @GetMapping("/setting/changePassword")
  public String changePassword() {
    return "front/user/setting/changePassword";
  }

  /**
   * 修改密码
   *
   * @param oldPassword
   * @param newPassword
   * @return
   */
  @PostMapping("/setting/changePassword")
  @ResponseBody
  public Result changePassword(String oldPassword, String newPassword) {
    User user = getUser();
    ApiAssert.notTrue(user.getBlock(), "你的帐户已经被禁用，不能进行此项操作");
    ApiAssert.isTrue(new BCryptPasswordEncoder().matches(oldPassword, user.getPassword()), "旧密码不正确");
    user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
    userService.save(user);
    return Result.success();
  }

  /**
   * user accessToken page
   *
   * @param model
   * @return
   */
  @GetMapping("/setting/accessToken")
  public String accessToken(Model model) {
    model.addAttribute("user", getUser());
    return "/front/user/setting/accessToken";
  }

  /**
   * 刷新token
   *
   * @param response
   * @return
   */
  @GetMapping("/setting/refreshToken")
  public String refreshToken(HttpServletResponse response) {
    User user = getUser();
    user.setToken(UUID.randomUUID().toString());
    userService.save(user);
    return redirect(response, "/user/setting/accessToken");
  }

  /**
   * query user log history
   *
   * @param p     page
   * @param model
   * @return
   */
  @GetMapping("/setting/log")
  public String scoreLog(@RequestParam(defaultValue = "1") Integer p, Model model) {
    User user = getUser();
    model.addAttribute("page", logService.findByUserId(p, siteConfig.getPageSize(), user.getId()));
    return "front/user/setting/log";
  }

}
