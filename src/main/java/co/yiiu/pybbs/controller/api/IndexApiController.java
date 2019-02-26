package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.service.TagService;
import co.yiiu.pybbs.service.TopicService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.CookieUtil;
import co.yiiu.pybbs.util.FileUtil;
import co.yiiu.pybbs.util.MyPage;
import co.yiiu.pybbs.util.Result;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@RestController
@RequestMapping("/api")
public class IndexApiController extends BaseApiController {

  @Autowired
  private UserService userService;
  @Autowired
  private SystemConfigService systemConfigService;
  @Autowired
  private CookieUtil cookieUtil;
  @Autowired
  private TopicService topicService;
  @Autowired
  private TagService tagService;
  @Autowired
  private FileUtil fileUtil;

  // 首页接口
  @GetMapping({"/", "/index"})
  public Result index(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "all") String tab){
    MyPage<Map<String, Object>> page = topicService.selectAll(pageNo, tab);
    return success(page);
  }

  // 处理登录的接口
  @PostMapping("/login")
  public Result login(@RequestBody Map<String, String> body, HttpSession session) {
    String username = body.get("username");
    String password = body.get("password");
    String captcha = body.get("captcha");
    String _captcha = (String) session.getAttribute("_captcha");
    ApiAssert.notTrue(_captcha == null || StringUtils.isEmpty(captcha), "请输入验证码");
    ApiAssert.notTrue(!_captcha.equalsIgnoreCase(captcha), "验证码不正确");
    ApiAssert.notEmpty(username, "请输入用户名");
    ApiAssert.notEmpty(password, "请输入密码");
    User user = userService.selectByUsername(username);
    ApiAssert.notNull(user, "用户不存在");
    ApiAssert.isTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()), "用户名或密码不正确");
    return this.doUserStorage(session, user);
  }

  // 处理注册的接口
  @PostMapping("/register")
  public Result register(@RequestBody Map<String, String> body, HttpSession session) {
    String username = body.get("username");
    String password = body.get("password");
    String captcha = body.get("captcha");
    String _captcha = (String) session.getAttribute("_captcha");
    ApiAssert.notTrue(_captcha == null || StringUtils.isEmpty(captcha), "请输入验证码");
    ApiAssert.notTrue(!_captcha.equalsIgnoreCase(captcha), "验证码不正确");
    ApiAssert.notEmpty(username, "请输入用户名");
    ApiAssert.notEmpty(password, "请输入密码");
    User user = userService.selectByUsername(username);
    ApiAssert.isNull(user, "用户名已存在");
    user = userService.addUser(username, password, null, null, null, null);
    return this.doUserStorage(session, user);
  }

  // 登录成功后，处理的逻辑一样，这里提取出来封装一个方法处理
  private Result doUserStorage(HttpSession session, User user) {
    // 将用户信息写session
    if (session != null) {
      session.setAttribute("_user", user);
      session.removeAttribute("_captcha");
    }
    // 将用户token写cookie
    cookieUtil.setCookie(systemConfigService.selectAllConfig().get("cookie_name").toString(), user.getToken());
    Map<String, Object> map = new HashMap<>();
    map.put("user", user);
    map.put("token", user.getToken());
    return success(map);
  }

  // 标签接口
  @GetMapping("/tags")
  public Result tags(@RequestParam(defaultValue = "1") Integer pageNo) {
    return success(tagService.selectAll(pageNo, null, null));
  }

  // 上传图片
  @PostMapping("/upload")
  @ResponseBody
  public Result upload(@RequestParam("file") MultipartFile file, String type, HttpSession session) {
    User user = getApiUser();
    ApiAssert.notEmpty(type, "上传图片类型不能为空");
    long size = file.getSize();
    int uploadAvatarSizeLimit = Integer.parseInt(systemConfigService.selectAllConfig().get("upload_avatar_size_limit").toString());
    if (size > uploadAvatarSizeLimit * 1024 * 1024) return error("文件太大了，请上传文件大小在 " + uploadAvatarSizeLimit + "MB 以内");
    String url;
    if (type.equalsIgnoreCase("avatar")) { // 上传头像
      // 拿到上传后访问的url
      url = fileUtil.upload(file, "avatar", "avatar/" + user.getUsername());
      if (url != null) {
        // 查询当前用户的最新信息
        User user1 = userService.selectById(user.getId());
        user1.setAvatar(url);
        // 保存用户新的头像
        userService.update(user1);
        // 将最新的用户信息更新在session里
        if (session != null) session.setAttribute("_user", user1);
      }
    } else if (type.equalsIgnoreCase("topic")) { // 发帖上传图片
      url = fileUtil.upload(file, null, "topic/" + user.getUsername());
    } else {
      return error("上传图片类型不在处理范围内");
    }
    if (url == null) return error("上传的文件不存在或者上传过程发生了错误，请重试一下");
    return success(url);
  }
}
