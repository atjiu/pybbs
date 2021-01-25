package co.yiiu.pybbs.controller.admin;

import co.yiiu.pybbs.service.ICommentService;
import co.yiiu.pybbs.service.ITagService;
import co.yiiu.pybbs.service.ITopicService;
import co.yiiu.pybbs.service.IUserService;
import com.sun.management.OperatingSystemMXBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.management.ManagementFactory;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
public class IndexAdminController extends BaseAdminController {

    private final Logger log = LoggerFactory.getLogger(IndexAdminController.class);

    @Resource
    private ITopicService topicService;
    @Resource
    private ITagService tagService;
    @Resource
    private ICommentService commentService;
    @Resource
    private IUserService userService;

    @RequiresUser
    @GetMapping({"/admin/", "/admin/index"})
    public String index(Model model) {
        // 查询当天新增话题
        model.addAttribute("topic_count", topicService.countToday());
        // 查询当天新增标签
        model.addAttribute("tag_count", tagService.countToday());
        // 查询当天新增评论
        model.addAttribute("comment_count", commentService.countToday());
        // 查询当天新增用户
        model.addAttribute("user_count", userService.countToday());

        // 获取操作系统的名字
        model.addAttribute("os_name", System.getProperty("os.name"));

        // 内存
        int kb = 1024;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
        //已使用的物理内存
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
        // 获取系统cpu负载
        double systemCpuLoad = osmxb.getSystemCpuLoad();
        // 获取jvm线程负载
        double processCpuLoad = osmxb.getProcessCpuLoad();

        model.addAttribute("totalMemorySize", totalMemorySize);
        model.addAttribute("usedMemory", usedMemory);
        model.addAttribute("systemCpuLoad", systemCpuLoad);
        model.addAttribute("processCpuLoad", processCpuLoad);

        return "admin/index";
    }

    // 登录后台
    @GetMapping("/adminlogin")
    public String adminlogin() {
        return "admin/login";
    }

    // 处理后台登录逻辑
    @PostMapping("/admin/login")
    public String adminlogin(String username, String password, String code, HttpSession session,
                             @RequestParam(defaultValue = "0") Boolean rememberMe,
                             HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String url = WebUtils.getSavedRequest(request) == null ? "/admin/index" : WebUtils.getSavedRequest(request).getRequestUrl();
        String captcha = (String) session.getAttribute("_captcha");
        if (captcha == null || StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            redirectAttributes.addFlashAttribute("error", "验证码不正确");
            redirectAttributes.addFlashAttribute("username", username);
            url = "/adminlogin";
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

    @GetMapping("/admin/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return redirect("/adminlogin");
    }

    @GetMapping("/admin/no_auth")
    public String noAuth() {
        return redirect("/adminlogin");
    }
}
