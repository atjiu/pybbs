package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.model.Code;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.ICodeService;
import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.service.IUserService;
import co.yiiu.pybbs.util.CookieUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
public class IndexController extends BaseController {

    private Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private ISystemConfigService systemConfigService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICodeService codeService;

    // 首页
    @GetMapping({"/", "/index", "/index.html"})
    public String index(@RequestParam(defaultValue = "all") String tab, @RequestParam(defaultValue = "1") Integer
            pageNo, Boolean active, Model model) {
        model.addAttribute("tab", tab);
        model.addAttribute("active", active);
        model.addAttribute("pageNo", pageNo);

        return render("index");
    }

    @GetMapping("/top100")
    public String top100() {
        return render("top100");
    }

    @GetMapping("/settings")
    public String settings(HttpSession session, Model model) {
        // 再查一遍，保证数据的最新
        User user = (User) session.getAttribute("_user");
        user = userService.selectById(user.getId());
        model.addAttribute("user", user);
        return render("user/settings");
    }

    @GetMapping("/tags")
    public String tags(@RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        model.addAttribute("pageNo", pageNo);
        return render("tag/tags");
    }

    // 登录
    @GetMapping("/login")
    public String login() {
        User user = getUser();
        if (user != null) return redirect("/");
        return render("login");
    }

    // 注册
    @GetMapping("/register")
    public String register() {
        User user = getUser();
        if (user != null) return redirect("/");
        return render("register");
    }

    // 通知
    @GetMapping("/notifications")
    public String notifications() {
        return render("notifications");
    }

    // 登出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("_user") != null) {
            User user = (User) session.getAttribute("_user");
            // 清除redis里的缓存
            userService.delRedisUser(user);
            // 清除session里的用户信息
            session.removeAttribute("_user");
            // 清除cookie里的用户token
            cookieUtil.clearCookie(systemConfigService.selectAllConfig().get("cookie_name").toString());
        }
        return redirect("/");
    }

    // 登录后台
    @GetMapping("/adminlogin")
    public String adminlogin(HttpServletRequest request) {
        return "admin/login";
    }

    // 处理后台登录逻辑
    @PostMapping("/adminlogin")
    public String adminlogin(String username, String password, String code, HttpSession session, @RequestParam(defaultValue = "0") Boolean rememberMe,
                             HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String url = WebUtils.getSavedRequest(request) == null ? "/admin/index" : WebUtils.getSavedRequest(request).getRequestUrl();
        String captcha = (String) session.getAttribute("_captcha");
        if (captcha == null || StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            redirectAttributes.addFlashAttribute("error", "验证码不正确");
        } else {
            try {
                // 添加用户认证信息
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
                //进行验证，这里可以捕获异常，然后返回对应信息
                subject.login(token);
            } catch (UnknownAccountException e) {
                log.error(e.getMessage());
                redirectAttributes.addFlashAttribute("error", "用户不存在");
                redirectAttributes.addFlashAttribute("username", username);
                url = "/adminlogin";
            } catch (IncorrectCredentialsException e) {
                log.error(e.getMessage());
                redirectAttributes.addFlashAttribute("error", "密码错误");
                redirectAttributes.addFlashAttribute("username", username);
                url = "/adminlogin";
//            } catch (LockedAccountException e) {
//                log.error(e.getMessage());
//                redirectAttributes.addFlashAttribute("error", "用户被锁定");
//                redirectAttributes.addFlashAttribute("username", username);
//                url = "/adminlogin";
//            } catch (ExcessiveAttemptsException e) {
//                log.error(e.getMessage());
//                redirectAttributes.addFlashAttribute("error", "登录尝试次数过多");
//                redirectAttributes.addFlashAttribute("username", username);
//                url = "/adminlogin";
            } catch (AuthenticationException e) {
                log.error(e.getMessage());
                redirectAttributes.addFlashAttribute("error", "用户名或密码错误");
                redirectAttributes.addFlashAttribute("username", username);
                url = "/adminlogin";
            }
        }
        return redirect(url);
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam String keyword, Model model) {
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("keyword", keyword);
        return render("search");
    }

    // 切换语言
    @GetMapping("changeLanguage")
    public String changeLanguage(String lang, HttpSession session, HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if ("zh".equals(lang)) {
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.SIMPLIFIED_CHINESE);
        } else if ("en".equals(lang)) {
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.US);
        }
        return StringUtils.isEmpty(referer) ? redirect("/") : redirect(referer);
    }

    // 激活帐号
    @GetMapping("/active")
    public String active(String email, String code, HttpSession session) {
        Assert.notNull(email, "激活邮箱不能为空");
        Assert.notNull(code, "激活码不能为空");
        User user = (User) session.getAttribute("_user");
        if (user == null) {
            user = userService.selectByEmail(email);
        } else {
            Assert.isTrue(email.equals(user.getEmail()), "激活的邮箱跟当前用户帐号注册的邮箱不一致");
        }
        Assert.notNull(user, "激活的邮箱还没有注册过，请先注册");
        Code code1 = codeService.validateCode(user.getId(), email, null, code);
        Assert.notNull(code1, "激活链接失效或者激活码错误");
        // 将code的状态置为已用
        code1.setUsed(true);
        codeService.update(code1);
        // 修改当前用户的状态
        user.setActive(true);
        userService.update(user);
        session.setAttribute("_user", user);
        return redirect("/?active=true");
    }
}
