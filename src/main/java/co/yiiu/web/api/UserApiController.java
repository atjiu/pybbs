package co.yiiu.web.api;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.identicon.Identicon;
import co.yiiu.core.util.security.Base64Helper;
import co.yiiu.core.util.security.crypto.BCryptPasswordEncoder;
import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.comment.service.CommentService;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * Created by tomoya at 2018/4/12
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private Identicon identicon;
  @Autowired
  private LogService logService;

  /**
   * 用户的个人信息
   *
   * @param username 用户名
   * @return
   */
  @GetMapping("/{username}")
  public Result profile(@PathVariable String username) {
    return Result.success(userService.findByUsername(username));
  }

  /**
   * 用户的话题
   *
   * @param username 用户名
   * @param pageNo   页码
   * @param pageSize 每页显示的条数，最多不能超过 siteConfig.getPageSize()
   * @return
   */
  @GetMapping("/{username}/topics")
  public Result topics(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Integer pageSize) {
    User currentUser = userService.findByUsername(username);
    Page<Map> page = topicService.findByUser(pageNo,
        pageSize > siteConfig.getPageSize() ? siteConfig.getPageSize() : pageSize, currentUser);
    return Result.success(page);
  }

  /**
   * 用户的评论
   *
   * @param username 用户名
   * @param pageNo   页码
   * @param pageSize 每页显示的条数，最多不能超过 siteConfig.getPageSize()
   * @return
   */
  @GetMapping("/{username}/comments")
  public Result comments(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Integer pageSize) {
    User currentUser = userService.findByUsername(username);
    Page<Map> page = commentService.findByUser(pageNo,
        pageSize > siteConfig.getPageSize() ? siteConfig.getPageSize() : pageSize, currentUser);
    return Result.success(page);
  }

  /**
   * 用户的收藏
   *
   * @param username 用户名
   * @param pageNo   页码
   * @return
   */
  @GetMapping("/{username}/collects")
  public Result collects(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo) {
    User currentUser = userService.findByUsername(username);
    Page<Map> page = collectService.findByUserId(pageNo, siteConfig.getPageSize(), currentUser.getId());
    return Result.success(page);
  }

  /**
   * 更新用户的个人设置
   *
   * @param email        个人邮箱
   * @param url
   * @param bio
   * @param commentEmail
   * @param replyEmail
   * @return
   */
  @PostMapping("/setting/profile")
  public Result updateUserInfo(String email, String url, String bio,
                               @RequestParam(defaultValue = "false") Boolean commentEmail,
                               @RequestParam(defaultValue = "false") Boolean replyEmail) {
    User user = getApiUser();
    ApiAssert.notTrue(user.getBlock(), "你的帐户已经被禁用，不能进行此项操作");
//    user.setEmail(email);
    if (bio != null && bio.trim().length() > 0) user.setBio(Jsoup.clean(bio, Whitelist.none()));
    user.setCommentEmail(commentEmail);
    user.setReplyEmail(replyEmail);
    user.setUrl(url);
    userService.save(user);
    return Result.success(user);
  }

  /**
   * 修改密码
   *
   * @param oldPassword 旧密码
   * @param newPassword 新密码
   * @return
   */
  @PostMapping("/setting/changePassword")
  public Result changePassword(String oldPassword, String newPassword) {
    User user = getApiUser();
    ApiAssert.notTrue(user.getBlock(), "你的帐户已经被禁用，不能进行此项操作");
    ApiAssert.isTrue(new BCryptPasswordEncoder().matches(oldPassword, user.getPassword()), "旧密码不正确");
    user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
    userService.save(user);
    return Result.success();
  }


  /**
   * 保存头像
   *
   * @param avatar 头像的Base64转码字符串
   * @return
   */
  @PostMapping("setting/changeAvatar")
  public Result changeAvatar(String avatar) {
    ApiAssert.notEmpty(avatar, "头像不能为空");
    String _avatar = avatar.substring(avatar.indexOf(",") + 1, avatar.length());
    User user = getUser();
    try {
      byte[] bytes = Base64Helper.decode(_avatar);
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      BufferedImage bufferedImage = ImageIO.read(bais);
      String __avatar = null;
      if(siteConfig.getUploadType().equals("local")) {
        __avatar = identicon.saveFile(user.getUsername(), bufferedImage);
      } else if(siteConfig.getUploadType().equals("qiniu")) {
        __avatar = identicon.saveFileToQiniu(bytes);
      }
      user.setAvatar(__avatar);
      userService.save(user);
      bais.close();
    } catch (Exception e) {
      e.printStackTrace();
      return Result.error("头像格式不正确");
    }
    return Result.success();
  }

  /**
   * 刷新token
   *
   * @return
   */
  @GetMapping("/setting/refreshToken")
  public Result refreshToken() {
    User user = getApiUser();
    userService.save(user);
    return Result.success();
  }


  /**
   * 用户的日志列表
   *
   * @param pageNo 页码
   * @return
   */
  @GetMapping("/setting/log")
  public Result scoreLog(@RequestParam(defaultValue = "1") Integer pageNo) {
    User user = getApiUser();
    return Result.success(logService.findByUserId(pageNo, siteConfig.getPageSize(), user.getId()));
  }
}
