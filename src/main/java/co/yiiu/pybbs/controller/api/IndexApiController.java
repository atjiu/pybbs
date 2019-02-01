package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.service.TagService;
import co.yiiu.pybbs.service.TopicService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.CookieUtil;
import co.yiiu.pybbs.util.MyPage;
import co.yiiu.pybbs.util.Result;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

  // 首页接口
  @GetMapping({"/", "/index"})
  public Result index(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "all") String tab){
    MyPage<Map<String, Object>> page = topicService.selectAll(pageNo, tab);
    return success(page);
  }

  // 处理登录的接口
  @PostMapping("/login")
  public Result login(String username, String password, HttpSession session) {
    ApiAssert.notEmpty(username, "请输入用户名");
    ApiAssert.notEmpty(password, "请输入密码");
    User user = userService.selectByUsername(username);
    ApiAssert.notNull(user, "用户不存在");
    ApiAssert.isTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()), "用户名或密码不正确");
    return this.doUserStorage(session, user);
  }

  // 处理注册的接口
  @PostMapping("/register")
  public Result register(String username, String password, HttpSession session) {
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
    if (session != null) session.setAttribute("_user", user);
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

}
