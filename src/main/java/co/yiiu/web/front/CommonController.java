package co.yiiu.web.front;

import co.yiiu.config.LogEventConfig;
import co.yiiu.config.MailTemplateConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiException;
import co.yiiu.core.util.*;
import co.yiiu.module.attachment.model.Attachment;
import co.yiiu.module.attachment.service.AttachmentService;
import co.yiiu.module.code.service.CodeService;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by tomoya at 2018/1/12
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private MailTemplateConfig mailTemplateConfig;
  @Autowired
  private FileUtil fileUtil;
  @Autowired
  private CodeService codeService;
  @Autowired
  FreemarkerUtil freemarkerUtil;
  @Autowired
  LogEventConfig logEventConfig;
  @Autowired
  LogService logService;
  @Autowired
  private EmailUtil emailUtil;
  @Autowired
  private AttachmentService attachmentService;

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

    String genCode = codeService.genEmailCode(email);
    Map<String, Object> params = Maps.newHashMap();
    params.put("genCode", genCode);
    String subject = freemarkerUtil.format((String) mailTemplateConfig.getRegister().get("subject"), params);
    String content = freemarkerUtil.format((String) mailTemplateConfig.getRegister().get("content"), params);
    if(emailUtil.sendEmail(email, subject, content)) {
      return Result.success();
    } else {
      return Result.error("邮件发送失败");
    }
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
    String username = getUser().getUsername();
    if (!file.isEmpty()) {
      try {
        String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
        Attachment attachment = attachmentService.findByMd5(md5);
        if (attachment == null) {
          attachment = fileUtil.uploadFile(file, FileType.PICTURE, username);
        }
        return Result.success(attachment.getRequestUrl());
      } catch (IOException e) {
        e.printStackTrace();
        return Result.error("上传失败");
      }
    }
    return Result.error("文件不存在");
  }

  // wangEditor 上传
  @PostMapping("/wangEditorUpload")
  @ResponseBody
  public Map<String, Object> wangEditorUpload(@RequestParam("file") MultipartFile file) {
    String username = getUser().getUsername();
    Map<String, Object> map = new HashMap<>();
    if (!file.isEmpty()) {
      try {
        String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
        Attachment attachment = attachmentService.findByMd5(md5);
        if (attachment == null) {
          attachment = fileUtil.uploadFile(file, FileType.PICTURE, username);
        }
        map.put("errno", 0);
        map.put("data", Arrays.asList(attachment.getRequestUrl()));
      } catch (IOException e) {
        e.printStackTrace();
        map.put("errno", 2);
        map.put("desc", e.getLocalizedMessage());
      }
    } else {
      map.put("errno", 1);
      map.put("desc", "请选择图片");
    }
    return map;
  }
}
