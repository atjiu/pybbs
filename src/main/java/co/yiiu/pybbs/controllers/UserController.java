package co.yiiu.pybbs.controllers;

import co.yiiu.pybbs.conf.properties.SiteConfig;
import co.yiiu.pybbs.exceptions.ApiAssert;
import co.yiiu.pybbs.models.Collect;
import co.yiiu.pybbs.models.Comment;
import co.yiiu.pybbs.models.Topic;
import co.yiiu.pybbs.models.User;
import co.yiiu.pybbs.services.*;
import co.yiiu.pybbs.utils.Result;
import co.yiiu.pybbs.utils.StringUtil;
import co.yiiu.pybbs.utils.bcrypt.BCryptPasswordEncoder;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tomoya at 2018/9/3
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private AccessTokenService accessTokenService;
  @Autowired
  private StringUtil stringUtil;
  @Autowired
  private SiteConfig siteConfig;

  @GetMapping("/{username}")
  public Result index(@PathVariable String username) {
    User user = userService.findByUsername(username);
    return Result.success(user);
  }

  @GetMapping("/{username}/topics")
  public Result topics(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Integer pageSize) {
    if (pageSize != null && pageSize > siteConfig.getPageSize()) pageSize = siteConfig.getPageSize();
    User user = userService.findByUsername(username);
    ApiAssert.notNull(user, "用户不存在");
    Page<Topic> page = topicService.findByUserId(user.getId(), pageNo, pageSize);
    page.getContent().forEach(topic -> topic.setUser(user));
    return Result.success(page);
  }

  @GetMapping("/{username}/comments")
  public Result comments(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Integer pageSize) {
    if (pageSize != null && pageSize > siteConfig.getPageSize()) pageSize = siteConfig.getPageSize();
    User user = userService.findByUsername(username);
    ApiAssert.notNull(user, "用户不存在");
    Page<Comment> page = commentService.findByUserId(user.getId(), pageNo, pageSize);
    page.getContent().forEach(comment -> {
      comment.setUser(user);
      comment.setTopic(topicService.findById(comment.getTopicId()));
      if (comment.getCommentId() != null) comment.setComment(commentService.findById(comment.getCommentId()));
    });
    return Result.success(page);
  }

  @GetMapping("/{username}/collects")
  public Result collects(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Integer pageSize) {
    if (pageSize != null && pageSize > siteConfig.getPageSize()) pageSize = siteConfig.getPageSize();
    User user = userService.findByUsername(username);
    ApiAssert.notNull(user, "用户不存在");
    Page<Collect> page = collectService.findByUserId(user.getId(), pageNo, pageSize);
    page.getContent().forEach(collect -> {
      collect.setUser(user);
      collect.setTopic(topicService.findById(collect.getTopicId()));
    });
    return Result.success(page);
  }

  @PostMapping("/settings/profile")
  public Result profile(String email, String website, String bio, String avatar) {
    User user = getUser();
    if (!StringUtils.isEmpty(email)) {
      ApiAssert.isTrue(stringUtil.check(email, stringUtil.emailRegex), "请输入正确的邮箱地址");
      User _user = userService.findByEmail(email);
      ApiAssert.isNull(_user, "邮箱被占用了");
      user.setEmail(email);
    }
    if (!StringUtils.isEmpty(website)) {
      ApiAssert.isTrue(stringUtil.check(website, stringUtil.urlRegex), "请输入正确的网址");
      user.setWebsite(website);
    }
    if (!StringUtils.isEmpty(bio)) user.setBio(Jsoup.clean(bio, Whitelist.none()));
    if (!StringUtils.isEmpty(avatar)) user.setAvatar(Jsoup.clean(avatar, Whitelist.none()));
    userService.save(user);
    return Result.success();
  }

  @PostMapping("/settings/updatePassword")
  public Result updatePassword(String rawPassword, String newPassword) {
    User user = getUser();
    ApiAssert.notEmpty(rawPassword, "旧密码不能为空");
    ApiAssert.notEmpty(newPassword, "新密码不能为空");
    ApiAssert.isTrue(stringUtil.check(newPassword, stringUtil.passwordRegex), "密码只能输入[0-9a-zA-Z]，长度6-32位");
    ApiAssert.isTrue(new BCryptPasswordEncoder().matches(rawPassword, user.getPassword()), "原密码不正确");
    user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
    // 删除用户的所有token
    accessTokenService.deleteByUserId(user.getId());
    return Result.success();
  }

}
