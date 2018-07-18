package co.yiiu.web.api;

import co.yiiu.config.MailTemplateConfig;
import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiException;
import co.yiiu.core.util.*;
import co.yiiu.module.attachment.model.Attachment;
import co.yiiu.module.attachment.service.AttachmentService;
import co.yiiu.module.code.service.CodeService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomoya at 2018/4/12
 */
@RestController
@RequestMapping("/api/common")
public class CommonApiController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private MailTemplateConfig mailTemplateConfig;
  @Autowired
  private FileUtil fileUtil;
  @Autowired
  private EmailUtil emailUtil;
  @Autowired
  private CodeService codeService;
  @Autowired
  FreemarkerUtil freemarkerUtil;
  @Autowired
  private AttachmentService attachmentService;
  @Autowired
  private SiteConfig siteConfig;

  @GetMapping("/sendEmailCode")
  public Result sendEmailCode(String email) throws ApiException {
    if (!StrUtil.check(email, StrUtil.check)) throw new ApiException("请输入正确的Email");

    User user = userService.findByEmail(email);
    if (user != null) throw new ApiException("邮箱已经被使用");

    String genCode = codeService.genEmailCode(email);
    Map<String, Object> params = Maps.newHashMap();
    params.put("genCode", genCode);
    String subject = freemarkerUtil.format((String) mailTemplateConfig.getRegister().get("subject"), params);
    String content = freemarkerUtil.format((String) mailTemplateConfig.getRegister().get("content"), params);
    emailUtil.sendEmail(email, subject, content);
    return Result.success();
  }

  /**
   * wangEditor 上传
   *
   * @param file 上传的文件名为file
   * @return
   */
  @PostMapping("/wangEditorUpload")
  public Map<String, Object> wangEditorUpload(@RequestParam("file") MultipartFile file) {
    String username = getUser().getUsername();
    Map<String, Object> map = new HashMap<>();
    if (!file.isEmpty()) {
      try {
        String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
        Attachment attachment = attachmentService.findByMd5(md5);
        if (attachment == null) {
          if (siteConfig.getUploadType().equals("local")) {
            attachment = fileUtil.uploadFile(file, FileType.PICTURE, username);
          } else if (siteConfig.getUploadType().equals("qiniu")) {
            attachment = fileUtil.uploadFileToQiniu(file, FileType.PICTURE);
          }
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
