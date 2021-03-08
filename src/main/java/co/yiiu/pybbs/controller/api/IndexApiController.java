package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.Code;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.*;
import co.yiiu.pybbs.util.*;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@RestController
@RequestMapping("/api")
public class IndexApiController extends BaseApiController {

    @Resource
    private IUserService userService;
    @Resource
    private ISystemConfigService systemConfigService;
    @Resource
    private CookieUtil cookieUtil;
    @Resource
    private ITopicService topicService;
    @Resource
    private ITagService tagService;
    @Resource
    private FileUtil fileUtil;
    @Resource
    private ICodeService codeService;

    // 首页接口
    @GetMapping({"/", "/index"})
    public Result index(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "all") String
            tab) {
        MyPage<Map<String, Object>> page = topicService.selectAll(pageNo, tab);
        for (Map<String, Object> map : page.getRecords()) {
            Object content = map.get("content");
            map.put("content", StringUtils.isEmpty(content) ? null : SensitiveWordUtil.replaceSensitiveWord(content
                    .toString(), "*", SensitiveWordUtil.MinMatchType));
        }
        return success(page);
    }

    // 根据标签名返回标签下话题
    @GetMapping("/tag/{name}")
    public Result topicsByTagName(@RequestParam(defaultValue = "1") Integer pageNo, @PathVariable String name) {
        Tag tag = tagService.selectByName(name);
        if (tag == null) {
            return error("标签不存在");
        } else {
            MyPage<Map<String, Object>> iPage = tagService.selectTopicByTagId(tag.getId(), pageNo);
            Map<String, Object> result = new HashMap<>();
            result.put("tag", tag);
            result.put("page", iPage);
            return success(result);
        }
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
        String email = body.get("email");
        String captcha = body.get("captcha");
        String _captcha = (String) session.getAttribute("_captcha");
        ApiAssert.notTrue(_captcha == null || StringUtils.isEmpty(captcha), "请输入验证码");
        ApiAssert.notTrue(!_captcha.equalsIgnoreCase(captcha), "验证码不正确");
        ApiAssert.notEmpty(username, "请输入用户名");
        ApiAssert.notEmpty(password, "请输入密码");
        ApiAssert.notEmpty(email, "请输入邮箱");
        ApiAssert.isTrue(StringUtil.check(username, StringUtil.USERNAMEREGEX), "用户名只能为a-z,A-Z,0-9组合且2-16位");
        ApiAssert.isTrue(StringUtil.check(email, StringUtil.EMAILREGEX), "请输入正确的邮箱地址");
        User user = userService.selectByUsername(username);
        ApiAssert.isNull(user, "用户名已存在");
        User emailUser = userService.selectByEmail(email);
        ApiAssert.isNull(emailUser, "这个邮箱已经被注册过了，请更换一个邮箱");
        user = userService.addUser(username, password, null, email, null, null, true);
        return this.doUserStorage(session, user);
    }

    // 发送手机验证码
    @GetMapping("/sms_code")
    public Result sms_code(String captcha, String mobile, HttpSession session) {
        String _captcha = (String) session.getAttribute("_captcha");
        ApiAssert.notTrue(_captcha == null || StringUtils.isEmpty(captcha), "请输入验证码");
        ApiAssert.notTrue(!_captcha.equalsIgnoreCase(captcha), "验证码不正确");
        ApiAssert.notEmpty(mobile, "请输入手机号");
        ApiAssert.isTrue(StringUtil.check(mobile, StringUtil.MOBILEREGEX), "请输入正确的手机号");
        boolean b = codeService.sendSms(mobile);
        if (!b) {
            return error("短信发送失败或者站长没有配置短信服务");
        } else {
            return success();
        }
    }

    // 手机号+验证码登录
    @PostMapping("/mobile_login")
    public Result mobile_login(@RequestBody Map<String, String> body, HttpSession session) {
        String mobile = body.get("mobile");
        String code = body.get("code");
        String captcha = body.get("captcha");
        String _captcha = (String) session.getAttribute("_captcha");
        ApiAssert.notTrue(_captcha == null || StringUtils.isEmpty(captcha), "请输入验证码");
        ApiAssert.notTrue(!_captcha.equalsIgnoreCase(captcha), "验证码不正确");
        ApiAssert.notEmpty(mobile, "请输入手机号");
        ApiAssert.isTrue(StringUtil.check(mobile, StringUtil.MOBILEREGEX), "请输入正确的手机号");
        ApiAssert.notEmpty(code, "请输入手机验证码");
        Code validateCode = codeService.validateCode(null, null, mobile, code);
        ApiAssert.notTrue(validateCode == null, "手机验证码错误");
        User user = userService.addUserWithMobile(mobile);
        return doUserStorage(session, user);
    }

    // 忘记密码要通过发送邮件来重置密码
    // 让我再想想怎么实现这个功能比较好
    // @PostMapping("/forget_password")
    //  public Result forget_password(@RequestBody Map<String, String> body, HttpSession session) {
    //    String email = body.get("email");
    //    String captcha = body.get("captcha");
    //    String _captcha = (String) session.getAttribute("_captcha");
    //    ApiAssert.notTrue(_captcha == null || StringUtils.isEmpty(captcha), "请输入验证码");
    //    ApiAssert.notTrue(!_captcha.equalsIgnoreCase(captcha), "验证码不正确");
    //    ApiAssert.notEmpty(email, "请输入邮箱");
    //    ApiAssert.isTrue(StringUtil.check(email, StringUtil.EMAILREGEX), "请输入正确的邮箱地址");
    //    emailService.send
    //  }

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
    public Result upload(@RequestParam("file") MultipartFile[] files, String type, HttpSession session) {
        User user = getApiUser();
        ApiAssert.isTrue(user.getActive(), "你的帐号还没有激活，请去个人设置页面激活帐号");
        ApiAssert.notEmpty(type, "上传文件类型不能为空");
        Map<String, Object> resultMap = new HashMap<>();
        List<String> urls = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String url;
            MultipartFile file = files[i];
            String suffix = "." + Objects.requireNonNull(file.getContentType()).split("/")[1];
            if (!Arrays.asList(".jpg", ".png", ".gif", ".jpeg", ".mp4").contains(suffix.toLowerCase())) {
                errors.add("第[" + (i + 1) + "]个文件异常: " + "文件格式不正确");
                continue;
            }
            long size = file.getSize();
            // 根据不同上传类型，对文件大小做校验
            if (type.equalsIgnoreCase("video")) {
                long uploadVideoSizeLimit = Long.parseLong(systemConfigService.selectAllConfig().get("upload_video_size_limit").toString());
                if (size > uploadVideoSizeLimit * 1024 * 1024) {
                    errors.add("第[" + (i + 1) + "]个文件异常: " + "文件太大了，请上传文件大小在 " + uploadVideoSizeLimit + "MB 以内");
                    continue;
                }
            } else {
                long uploadImageSizeLimit = Long.parseLong(systemConfigService.selectAllConfig().get("upload_image_size_limit").toString());
                if (size > uploadImageSizeLimit * 1024 * 1024) {
                    errors.add("第[" + (i + 1) + "]个文件异常: " + "文件太大了，请上传文件大小在 " + uploadImageSizeLimit + "MB 以内");
                    continue;
                }
            }
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
            } else if (type.equalsIgnoreCase("video")) { // 视频上传
                url = fileUtil.upload(file, null, "video/" + user.getUsername());
            } else {
                errors.add("第[" + (i + 1) + "]个文件异常: " + "上传图片类型不在处理范围内");
                continue;
            }
            if (url == null) {
                errors.add("第[" + (i + 1) + "]个文件异常: " + "上传的文件不存在或者上传过程发生了错误");
                continue;
            }
            urls.add(url);
        }
        resultMap.put("urls", urls);
        resultMap.put("errors", errors);
        return success(resultMap);
    }

}
