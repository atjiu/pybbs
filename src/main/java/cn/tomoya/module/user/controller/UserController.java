package cn.tomoya.module.user.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import cn.tomoya.util.FileUploadEnum;
import cn.tomoya.util.FileUtil;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * user profile
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}")
    public String profile(@PathVariable String username, Model model) {
        User currentUser = userService.findByUsername(username);
        if (currentUser != null) {
            model.addAttribute("user", getUser());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("collectCount", collectService.countByUser(currentUser));
            model.addAttribute("topicPage", topicService.findByUser(1, 7, currentUser));
            model.addAttribute("replyPage", replyService.findByUser(1, 7, currentUser));
            model.addAttribute("pageTitle", currentUser.getUsername() + " " + localeMessageSourceUtil.getMessage("site.page.user.info"));
        } else {
            model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.user.notExist"));
        }
        return render("/user/info");
    }

    /**
     * user topics
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/topics")
    public String topics(@PathVariable String username, Integer p, HttpServletResponse response, Model model) {
        User currentUser = userService.findByUsername(username);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("page", topicService.findByUser(p == null ? 1 : p, settingService.getPageSize(), currentUser));
            model.addAttribute("pageTitle", currentUser.getUsername() + " " + localeMessageSourceUtil.getMessage("site.page.user.topic"));
            return render("/user/topics");
        } else {
            return renderText(response, localeMessageSourceUtil.getMessage("site.page.user.notExist"));
        }
    }

    /**
     * user comments
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/replies")
    public String replies(@PathVariable String username, Integer p, HttpServletResponse response, Model model) {
        User currentUser = userService.findByUsername(username);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("page", replyService.findByUser(p == null ? 1 : p, settingService.getPageSize(), currentUser));
            model.addAttribute("pageTitle", currentUser.getUsername() + " " + localeMessageSourceUtil.getMessage("site.page.user.comment"));
            return render("/user/replies");
        } else {
            return renderText(response, localeMessageSourceUtil.getMessage("site.page.user.notExist"));
        }
    }

    /**
     * user collection topics
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/collects")
    public String collects(@PathVariable String username, Integer p, HttpServletResponse response, Model model) {
        User currentUser = userService.findByUsername(username);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("page", collectService.findByUser(p == null ? 1 : p, settingService.getPageSize(), currentUser));
            model.addAttribute("pageTitle", currentUser.getUsername() + " " + localeMessageSourceUtil.getMessage("site.page.user.collection"));
            return render("/user/collects");
        } else {
            return renderText(response, localeMessageSourceUtil.getMessage("site.page.user.notExist"));
        }
    }

    /**
     * modify user profile
     *
     * @param model
     * @return
     */
    @GetMapping("/setting")
    public String setting(Model model) {
        model.addAttribute("user", getUser());
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.user.setting"));
        return render("/user/setting");
    }

    /**
     * update user profile
     *
     * @param email
     * @param url
     * @param signature
     * @param response
     * @return
     */
    @PostMapping("/setting")
    public String updateUserInfo(String email, String url, String signature, @RequestParam("avatar") MultipartFile avatar, HttpServletResponse response) throws IOException {
        User user = getUser();
        if(user.isBlock()) return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.accountDisabled"));
        user.setEmail(email);
        user.setSignature(signature);
        user.setUrl(url);
        String requestUrl = fileUtil.uploadFile(avatar, FileUploadEnum.AVATAR);
        if (!StringUtils.isEmpty(requestUrl)) {
            user.setAvatar(requestUrl);
        }
        userService.updateUser(user);
        userService.clearCache();
        return redirect(response, "/user/" + user.getUsername());
    }

    /**
     * change password
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @PostMapping("/changePassword")
    public String changePassword(String oldPassword, String newPassword, Model model, HttpServletResponse response) {
        User user = getUser();
        if(user.isBlock()) return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.accountDisabled"));
        if(new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userService.updateUser(user);
            model.addAttribute("changePasswordErrorMsg", localeMessageSourceUtil.getMessage("site.prompt.text.user.updateSuccess"));
        } else {
            model.addAttribute("changePasswordErrorMsg", localeMessageSourceUtil.getMessage("site.prompt.text.user.oldPasswordIncorrect"));
        }
        model.addAttribute("user", getUser());
        return render("/user/setting");
    }

}
