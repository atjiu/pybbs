package co.yiiu.pybbs.controllers;

import co.yiiu.pybbs.conf.properties.QiniuConfig;
import co.yiiu.pybbs.conf.properties.SiteConfig;
import co.yiiu.pybbs.exceptions.ApiAssert;
import co.yiiu.pybbs.models.AccessToken;
import co.yiiu.pybbs.models.Topic;
import co.yiiu.pybbs.models.User;
import co.yiiu.pybbs.services.AccessTokenService;
import co.yiiu.pybbs.services.TopicService;
import co.yiiu.pybbs.services.UserService;
import co.yiiu.pybbs.utils.JsonUtil;
import co.yiiu.pybbs.utils.JwtTokenUtil;
import co.yiiu.pybbs.utils.Result;
import co.yiiu.pybbs.utils.StringUtil;
import co.yiiu.pybbs.utils.bcrypt.BCryptPasswordEncoder;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomoya at 2018/9/3
 */
@RestController
public class IndexController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private AccessTokenService accessTokenService;
  @Autowired
  private StringUtil stringUtil;
  @Autowired
  private TopicService topicService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private QiniuConfig qiniuConfig;

  @GetMapping("/")
  public Result index(@RequestParam(defaultValue = "1") Integer pageNo, String tab) {
    if (!StringUtils.isEmpty(tab) && !stringUtil.sectionValues().contains(tab)) tab = null;
    // 限制一下最多只能拉取20页数据
    if (pageNo > 20) return Result.success("没有更多数据了");
    Page<Topic> page = topicService.findAll(pageNo, siteConfig.getPageSize(), tab);
    page.getContent().forEach(topic -> {
      //列表不输出内容，减小json大小
      topic.setContent(null);
      topic.setUser(userService.findById(topic.getUserId()));
    });
    return Result.success(page);
  }

  @GetMapping("/top100")
  public Result top100(@RequestParam(defaultValue = "7") Integer size) {
    // 最多只能查100个用户排名
    if (size > 100) size = 100;
    return Result.success(userService.findTop100(size));
  }

  @PostMapping("/login")
  public Result login(String username, String password) {
    ApiAssert.notEmpty(username, "用户名不能为空");
    ApiAssert.notEmpty(password, "密码不能为空");

    Map<String, Object> map = new HashMap<>();

    User user = userService.findByUsername(username);
    ApiAssert.isTrue(user != null && new BCryptPasswordEncoder().matches(password, user.getPassword()), "用户名或密码不正确");
    String token;
    // 保存token
    AccessToken accessToken = accessTokenService.findByUserId(user.getId());
    if (accessToken == null) {
      token = jwtTokenUtil.generateToken(username);
      accessToken = new AccessToken();
      accessToken.setToken(token);
      accessToken.setUserId(user.getId());
      accessToken.setInTime(new Date());
      accessTokenService.save(accessToken);
    } else {
      token = accessToken.getToken();
    }
    map.put("token", token);
    map.put("admin", siteConfig.getAdmin().contains(username));
    return Result.success(map);
  }

  @PostMapping("/register")
  public Result register(String username, String password) {
    ApiAssert.isTrue(stringUtil.check(username, stringUtil.usernameRegex), "用户名只能输入[0-9a-zA-Z]，长度4-16位");
    ApiAssert.isTrue(stringUtil.check(password, stringUtil.passwordRegex), "密码只能输入[0-9a-zA-Z]，长度6-32位");

    Map<String, Object> map = new HashMap<>();

    User user = userService.findByUsername(username);
    ApiAssert.isNull(user, "用户名已经存在");
    user = new User();
    user.setUsername(username);
    user.setPassword(new BCryptPasswordEncoder().encode(password));
    user.setInTime(new Date());
    user.setScore(0);
    userService.save(user);
    String token = jwtTokenUtil.generateToken(username);
    // 保存token
    AccessToken accessToken = new AccessToken();
    accessToken.setToken(token);
    accessToken.setUserId(user.getId());
    accessToken.setInTime(new Date());

    map.put("token", accessToken);
    accessTokenService.save(accessToken);
    return Result.success(map);
  }

  @GetMapping("/logout")
  public Result logout() {
    return Result.success("本地删除Token即可登出");
  }

  // 只有登录了才能进行上传
  @PostMapping("/upload")
  public Result upload(@RequestParam("file") MultipartFile file) throws IOException {
    ApiAssert.notTrue(StringUtils.isEmpty(qiniuConfig.getAccessKey()) ||
        StringUtils.isEmpty(qiniuConfig.getBucket()) ||
        StringUtils.isEmpty(qiniuConfig.getDomain()) ||
        StringUtils.isEmpty(qiniuConfig.getSecretKey()), "请先配置上传服务参数");
    //构造一个带指定Zone对象的配置类
    Configuration cfg = new Configuration(Zone.zone2());
    //...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);
    Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
    String uploadToken = auth.uploadToken(qiniuConfig.getBucket());
    Response response = uploadManager.put(file.getInputStream(), null, uploadToken, null, null);
    DefaultPutRet defaultPutRet = JsonUtil.jsonToObject(response.bodyString(), DefaultPutRet.class);
    String requestUrl = qiniuConfig.getDomain() + defaultPutRet.key;
    return Result.success(requestUrl);
  }
}
