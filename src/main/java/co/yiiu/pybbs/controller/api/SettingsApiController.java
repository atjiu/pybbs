package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.Code;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.CodeService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.Result;
import co.yiiu.pybbs.util.StringUtil;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@RestController
@RequestMapping("/api/settings")
public class SettingsApiController extends BaseApiController {

  @Autowired
  private UserService userService;
  @Autowired
  private CodeService codeService;

  // 更新用户个人信息
  @PutMapping
  public Result update(@RequestBody Map<String, String> body, HttpSession session) {
    User user = getApiUser();
    String telegramName = body.get("telegramName");
    String website = body.get("website");
    String bio = body.get("bio");
    Boolean emailNotification = Boolean.parseBoolean(body.get("emailNotification"));
    // 查询当前用户的最新信息
    User user1 = userService.selectById(user.getId());
    user1.setTelegramName(telegramName);
    user1.setWebsite(website);
    user1.setBio(bio);
    user1.setEmailNotification(emailNotification);
    userService.update(user1);
    //更新session里用户信息
    if (session != null) session.setAttribute("_user", user1);
    return success();
  }

  // 发送邮箱验证码
  @GetMapping("/sendEmailCode")
  public Result sendEmailCode(String email) {
    User user = getApiUser();
    ApiAssert.notEmpty(email, "请输入邮箱 ");
    ApiAssert.isTrue(StringUtil.check(email, StringUtil.EMAILREGEX), "邮箱格式不正确");
    if (codeService.sendEmail(user.getId(), email)) {
      return success();
    } else {
      return error("邮件发送失败，也可能是站长没有配置邮箱");
    }
  }

  // 更新用户邮箱
  @PutMapping("/updateEmail")
  public Result updateEmail(@RequestBody Map<String, String> body, HttpSession session) {
    User user = getApiUser();
    String email = body.get("email");
    String code = body.get("code");
    ApiAssert.notEmpty(email, "请输入邮箱 ");
    ApiAssert.isTrue(StringUtil.check(email, StringUtil.EMAILREGEX), "邮箱格式不正确");
    Code code1 = codeService.validateCode(user.getId(), email, code);
    if (code1 == null) return error("验证码错误");
    // 将code的状态置为已用
    code1.setUsed(true);
    codeService.update(code1);
    // 查询当前用户的最新信息
    User user1 = userService.selectById(user.getId());
    user1.setEmail(email);
    userService.update(user1);
    if (session != null) session.setAttribute("_user", user1);
    return success();
  }

  // 修改密码
  @PutMapping("/updatePassword")
  public Result updatePassword(@RequestBody Map<String, String> body, HttpSession session) {
    User user = getApiUser();
    String oldPassword = body.get("oldPassword");
    String newPassword = body.get("newPassword");
    ApiAssert.notEmpty(oldPassword, "请输入旧密码");
    ApiAssert.notEmpty(newPassword, "请输入新密码");
    ApiAssert.notTrue(oldPassword.equals(newPassword), "新密码怎么还是旧的？");
    ApiAssert.isTrue(new BCryptPasswordEncoder().matches(oldPassword, user.getPassword()), "旧密码不正确");
    // 查询当前用户的最新信息
    User user1 = userService.selectById(user.getId());
    user1.setPassword(new BCryptPasswordEncoder().encode(newPassword));
    userService.update(user1);
    // 将最新的用户信息更新在session里
    if (session != null) session.setAttribute("_user", user1);
    return success();
  }

  // 刷新token
  @GetMapping("/refreshToken")
  public Result refreshToken(HttpSession session) {
    User user = getApiUser();
    String token = StringUtil.uuid();
    user.setToken(token);
    userService.update(user);
    session.setAttribute("_user", user);
    return success(token);
  }

}
