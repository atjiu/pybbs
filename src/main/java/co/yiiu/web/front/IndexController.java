package co.yiiu.web.front;

import co.yiiu.config.ScoreEventConfig;
import co.yiiu.config.SiteConfig;
import co.yiiu.config.data.DataConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiException;
import co.yiiu.core.util.*;
import co.yiiu.core.util.identicon.Identicon;
import co.yiiu.module.attendance.model.Attendance;
import co.yiiu.module.attendance.service.AttendanceService;
import co.yiiu.module.code.model.CodeEnum;
import co.yiiu.module.code.service.CodeService;
import co.yiiu.module.score.model.ScoreEventEnum;
import co.yiiu.module.score.model.ScoreLog;
import co.yiiu.module.score.service.ScoreLogService;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.service.RoleService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicSearch;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
public class IndexController extends BaseController {

  @Autowired
  private TopicSearch topicSearch;
  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private Identicon identicon;
  @Autowired
  private CodeService codeService;
  @Autowired
  private RoleService roleService;
  @Autowired
  FreemarkerUtil freemarkerUtil;
  @Autowired
  ScoreEventConfig scoreEventConfig;
  @Autowired
  ScoreLogService scoreLogService;

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
   * top 100 user score
   *
   * @return
   */
  @GetMapping("/top100")
  public String top100() {
    return "front/top100";
  }

  /**
   * 搜索
   *
   * @param p
   * @param q
   * @param model
   * @return
   */
  @GetMapping("/search")
  public String search(Integer p, String q, Model model) {
    Page<Topic> page = topicSearch.search(p == null ? 1 : p, siteConfig.getPageSize(), q);
    model.addAttribute("page", page);
    model.addAttribute("q", q);
    return "front/search";
  }

  /**
   * 进入登录页
   *
   * @return
   */
  @GetMapping("/login")
  public String toLogin(String s, Model model, HttpServletResponse response) {
    if (getUser() != null) {
      return redirect(response, "/");
    }
    model.addAttribute("s", s);
    return "front/login";
  }

  /**
   * 进入注册页面
   *
   * @return
   */
  @GetMapping("/register")
  public String toRegister(HttpServletResponse response) {
    if (getUser() != null) {
      return redirect(response, "/");
    }
    return "front/register";
  }

  /**
   * 注册验证
   *
   * @param username
   * @param password
   * @return
   */
  @PostMapping("/register")
  @ResponseBody
  public Result register(String username, String password, String email, String emailCode, String code,
                         HttpSession session) throws ApiException {

    String genCaptcha = (String) session.getAttribute("index_code");
    if (StringUtils.isEmpty(code)) throw new ApiException("验证码不能为空");

    if (!genCaptcha.toLowerCase().equals(code.toLowerCase())) throw new ApiException("验证码错误");
    if (StringUtils.isEmpty(username)) throw new ApiException("用户名不能为空");
    if (StringUtils.isEmpty(password)) throw new ApiException("密码不能为空");
    if (StringUtils.isEmpty(email)) throw new ApiException("邮箱不能为空");

    if (siteConfig.getIllegalUsername().contains(username)
        || !StrUtil.check(username, StrUtil.userNameCheck)) throw new ApiException("用户名不合法");

    User user = userService.findByUsername(username);
    if (user != null) throw new ApiException("用户名已经被注册");

    User user_email = userService.findByEmail(email);
    if (user_email != null) throw new ApiException("邮箱已经被使用");

    int validateResult = codeService.validateCode(email, emailCode, CodeEnum.EMAIL);
    if (validateResult == 1) throw new ApiException("邮箱验证码不正确");
    if (validateResult == 2) throw new ApiException("邮箱验证码已过期");
    if (validateResult == 3) throw new ApiException("邮箱验证码已经被使用");

    Date now = new Date();
    // generator avatar
    String avatar = identicon.generator(username);

    user = new User();
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(new BCryptPasswordEncoder().encode(password));
    user.setInTime(now);
    user.setBlock(false);
    user.setToken(UUID.randomUUID().toString());
    user.setAvatar(avatar);
    user.setAttempts(0);
    user.setScore(siteConfig.getScore());
    user.setSpaceSize(siteConfig.getUserUploadSpaceSize());

    // set user's role
    Role role = roleService.findByName(siteConfig.getNewUserRole());
    Set roles = new HashSet();
    roles.add(role);
    user.setRoles(roles);

    userService.save(user);

    //region 记录积分log
    if (siteConfig.getScore() != 0) {
      ScoreLog scoreLog = new ScoreLog();

      scoreLog.setInTime(new Date());
      scoreLog.setEvent(ScoreEventEnum.REGISTER.getEvent());
      scoreLog.setChangeScore(user.getScore());
      scoreLog.setScore(user.getScore());
      scoreLog.setUser(user);

      Map<String, Object> params = Maps.newHashMap();
      params.put("scoreLog", scoreLog);
      params.put("user", user);
      String des = freemarkerUtil.format(scoreEventConfig.getTemplate().get(ScoreEventEnum.REGISTER.getName()), params);
      scoreLog.setEventDescription(des);
      scoreLogService.save(scoreLog);

    }
    //endregion 记录积分log
    return Result.success();
  }

}
