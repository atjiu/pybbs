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

  private Logger log = LoggerFactory.getLogger(IndexController.class);

  @Autowired
  private TopicSearch topicSearch;
  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private Identicon identicon;
  @Autowired
  private FileUtil fileUtil;
  @Autowired
  private JavaMailSender javaMailSender;
  @Autowired
  private CodeService codeService;
  @Autowired
  private Environment env;
  @Autowired
  private AttendanceService attendanceService;
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
   * Daily attendance
   *
   * @param request
   * @return
   */
  @GetMapping("/attendance")
  @ResponseBody
  public Result attendance(HttpServletRequest request, HttpServletResponse response) {
    String attendanceValue = StrUtil.getCookie(request, siteConfig.getCookie().getAttendanceName());

    Random random = new Random();
    Date now = new Date();

    User user = getUser();
    Date date1 = DateUtil.string2Date(
        DateUtil.formatDateTime(now, DateUtil.FORMAT_DATE) + " 00:00:00",
        DateUtil.FORMAT_DATETIME
    );
    Date date2 = DateUtil.string2Date(
        DateUtil.formatDateTime(now, DateUtil.FORMAT_DATE) + " 23:59:59",
        DateUtil.FORMAT_DATETIME
    );

    if (StringUtils.isEmpty(attendanceValue) || !"1".equals(attendanceValue)) {
      Attendance attendance = attendanceService.findByUserAndInTime(user, date1, date2);
      if (attendance == null) {
        int score = random.nextInt(51) + 1; // random score in 1 - 50
        // save attendance record
        attendance = new Attendance();
        attendance.setInTime(now);
        attendance.setScore(score);
        attendance.setUser(user);
        attendanceService.save(attendance);

        // update user score
        user.setScore(user.getScore() + score);
        userService.save(user);

        // 记录积分log
        ScoreLog scoreLog = new ScoreLog();

        scoreLog.setInTime(new Date());
        scoreLog.setEvent(ScoreEventEnum.DAILY_SIGN.getEvent());
        scoreLog.setChangeScore(score);
        scoreLog.setScore(user.getScore());
        scoreLog.setUser(user);

        Map<String, Object> params = Maps.newHashMap();
        params.put("scoreLog", scoreLog);
        params.put("user", user);
        String des = freemarkerUtil.format(scoreEventConfig.getTemplate().get(ScoreEventEnum.DAILY_SIGN.getName()), params);
        scoreLog.setEventDescription(des);
        scoreLogService.save(scoreLog);

        // write remark to cookie
        writeAttendanceCookie(response, now, date2);

        return Result.success(score);
      }
    }
    writeAttendanceCookie(response, now, date2);
    return Result.error("你今天已经签到过了");
  }

  private void writeAttendanceCookie(HttpServletResponse response, Date date1, Date date2) {
    int maxAge = Math.abs((int) ((date2.getTime() - date1.getTime()) / 1000)); // second
    StrUtil.setCookie(
        response,
        siteConfig.getCookie().getAttendanceName(), // name
        "1", // value
        maxAge, // maxAge
        true, // httpOnly
        siteConfig.getCookie().getDomain(), // domain
        "/" // path
    );
  }

  /**
   * upload file
   *
   * @param file
   * @return
   */
  @PostMapping("/upload")
  @ResponseBody
  public Result upload(@RequestParam("file") MultipartFile file) {
    if (!file.isEmpty()) {
      try {
        if (fileUtil.getTotalSizeOfFilesInDir(new File(siteConfig.getUploadPath() + getUsername())) + file.getSize()
            > getUser().getSpaceSize() * 1024 * 1024)
          return Result.error("你的上传空间不够了，请用积分去用户中心兑换");
        String requestUrl = fileUtil.uploadFile(file, FileUploadEnum.FILE, getUsername());
        return Result.success(requestUrl);
      } catch (IOException e) {
        e.printStackTrace();
        return Result.error("上传失败");
      }
    }
    return Result.error("文件不存在");
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

    if (!StrUtil.check(username, StrUtil.userNameCheck)) throw new ApiException("用户名不合法");

    User user = userService.findByUsername(username);
    if (user != null) throw new ApiException("用户名已经被注册");

    User user_email = userService.findByEmail(email);
    if (user_email != null) throw new ApiException("邮箱已经被使用");

    int validateResult = codeService.validateCode(emailCode, CodeEnum.EMAIL);
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

  private int width = 120;// 定义图片的width
  private int height = 32;// 定义图片的height
  private int codeCount = 4;// 定义图片上显示验证码的个数
  private int xx = 22;
  private int fontHeight = 26;
  private int codeY = 25;
  char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
      'V', 'W', 'X', 'Y', '3', '4', '5', '6', '7', '8'};

  /**
   * 验证码生成
   *
   * @param req
   * @param resp
   * @throws IOException
   */
  @RequestMapping("/code")
  public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // 定义图像buffer
    BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // Graphics2D gd = buffImg.createGraphics();
    // Graphics2D gd = (Graphics2D) buffImg.getGraphics();
    Graphics gd = buffImg.getGraphics();
    // 创建一个随机数生成器类
    Random random = new Random();
    // 将图像填充为白色
    gd.setColor(Color.WHITE);
    gd.fillRect(0, 0, width, height);

    // 创建字体，字体的大小应该根据图片的高度来定。
    Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
    // 设置字体。
    gd.setFont(font);

    // 画边框。
    gd.setColor(Color.BLACK);
    gd.drawRect(0, 0, width - 1, height - 1);

    // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
    gd.setColor(Color.BLACK);
    for (int i = 0; i < 40; i++) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int xl = random.nextInt(20);
      int yl = random.nextInt(20);
      gd.drawLine(x, y, x + xl, y + yl);
    }

    // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
    StringBuffer randomCode = new StringBuffer();
    int red, green, blue;

    // 随机产生codeCount数字的验证码。
    for (int i = 0; i < codeCount; i++) {
      // 得到随机产生的验证码数字。
      String code = String.valueOf(codeSequence[random.nextInt(29)]);
      // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
      red = random.nextInt(255);
      green = random.nextInt(255);
      blue = random.nextInt(255);

      // 用随机产生的颜色将验证码绘制到图像中。
      gd.setColor(new Color(red, green, blue));
      gd.drawString(code, (i + 1) * xx, codeY);
      // 将产生的四个随机数组合在一起。
      randomCode.append(code);
    }
    // 将四位数字的验证码保存到Session中。
    HttpSession session = req.getSession();
    session.setAttribute("index_code", randomCode.toString());
    // 禁止图像缓存。
    resp.setHeader("Pragma", "no-cache");
    resp.setHeader("Cache-Control", "no-cache");
    resp.setDateHeader("Expires", 0);
    resp.setContentType("image/jpeg");
    // 将图像输出到Servlet输出流中。
    ServletOutputStream sos = resp.getOutputStream();
    ImageIO.write(buffImg, "jpeg", sos);
  }

  @GetMapping("/sendEmailCode")
  @ResponseBody
  public Result sendEmailCode(String email) throws ApiException {
    if (!StrUtil.check(email, StrUtil.check)) throw new ApiException("请输入正确的Email");

    User user = userService.findByEmail(email);
    if (user != null) throw new ApiException("邮箱已经被使用");

    try {
      String genCode = codeService.genEmailCode(email);
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
      helper.setFrom(env.getProperty("spring.mail.username"));
      helper.setTo(email);

      Map<String, Object> params = Maps.newHashMap();
      params.put("genCode", genCode);
      helper.setSubject(freemarkerUtil.format(siteConfig.getMail().getSubject(), params));
      helper.setText(freemarkerUtil.format(siteConfig.getMail().getContent(), params), true);
      javaMailSender.send(mimeMessage);
      return Result.success();
    } catch (Exception e) {
      log.error(e.getMessage());
      return Result.error("邮件发送失败");
    }
  }

}
