package cn.tomoya.module.index.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.exception.Result;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import cn.tomoya.util.FileUploadEnum;
import cn.tomoya.util.FileUtil;
import cn.tomoya.util.LocaleMessageSourceUtil;
import cn.tomoya.util.identicon.Identicon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private Identicon identicon;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * Home
     *
     * @param tab
     * @param p   page number
     * @return
     */
    @GetMapping("/")
    public String index(String tab, Integer p, Model model) {
        String tab_all = localeMessageSourceUtil.getMessage("site.tab.all");
        String tab_good = localeMessageSourceUtil.getMessage("site.tab.good");
        String tab_unanswered = localeMessageSourceUtil.getMessage("site.tab.unanswered");
        String sections = localeMessageSourceUtil.getMessage("site.tab.sections");
        String pageTitle = localeMessageSourceUtil.getMessage("site.page.home");
        String sectionName = tab;
        if (StringUtils.isEmpty(tab)) tab = localeMessageSourceUtil.getMessage("site.tab.all");
        if (tab.equals(tab_all) || tab.equals(tab_good) || tab.equals(tab_unanswered)) {
            sectionName = sections;
        }
        Page<Topic> page = topicService.page(p == null ? 1 : p, settingService.getPageSize(), tab);
        model.addAttribute("page", page);
        model.addAttribute("tab", tab);
        model.addAttribute("tab_all", tab_all);
        model.addAttribute("tab_good", tab_good);
        model.addAttribute("tab_unanswered", tab_unanswered);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("sectionName", sectionName);
        model.addAttribute("user", getUser());
        return render("/index");
    }

    /**
     * Search
     *
     * @param p     page number
     * @param q     query word
     * @param model
     * @return
     */
    @GetMapping("/search")
    public String search(Integer p, String q, Model model) {
        Page<Topic> page = topicService.search(p == null ? 1 : p, settingService.getPageSize(), q);
        model.addAttribute("page", page);
        model.addAttribute("q", q);
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.search"));
        return render("/search");
    }

    /**
     * sign in page
     *
     * @return
     */
    @GetMapping("/login")
    public String toLogin(String s, Model model, HttpServletResponse response) {
        if (getUser() != null) {
            return redirect(response, "/");
        }
        model.addAttribute("s", s);
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.login"));
        return render("/login");
    }

    /**
     * sign up page
     *
     * @return
     */
    @GetMapping("/register")
    public String toRegister(HttpServletResponse response, Model model) {
        if (getUser() != null) {
            return redirect(response, "/");
        }
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.register"));
        return render("/register");
    }

    /**
     * sign up
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public String register(String username, String password, HttpServletResponse response, Model model) {
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("errors", localeMessageSourceUtil.getMessage("site.prompt.text.usernameExists"));
        } else if (StringUtils.isEmpty(username)) {
            model.addAttribute("errors", localeMessageSourceUtil.getMessage("site.prompt.text.usernameNotEmpty"));
        } else if (StringUtils.isEmpty(password)) {
            model.addAttribute("errors", localeMessageSourceUtil.getMessage("site.prompt.text.passwordNotEmpty"));
        } else {
            Date now = new Date();
            String avatarName = UUID.randomUUID().toString();
            identicon.generator(avatarName);
            user = new User();
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setInTime(now);
            user.setBlock(false);
            user.setAvatar(settingService.getBaseUrl() + "static/images/avatar/" + avatarName + ".png");
            userService.save(user);
            return redirect(response, "/login?s=reg");
        }
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.register"));
        return render("/register");
    }

    /**
     * upload
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String requestUrl = fileUtil.uploadFile(file, FileUploadEnum.FILE);
                return Result.success(requestUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error(localeMessageSourceUtil.getMessage("site.prompt.text.uploadFailed"));
            }
        }
        return Result.error(localeMessageSourceUtil.getMessage("site.prompt.text.fileNotExist"));
    }

    /**
     * wangEditor upload
     *
     * @param file
     * @return
     */
    @PostMapping("/wangEditorUpload")
    @ResponseBody
    public String wangEditorUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                return fileUtil.uploadFile(file, FileUploadEnum.FILE);
            } catch (IOException e) {
                e.printStackTrace();
                return "error|" + localeMessageSourceUtil.getMessage("site.prompt.text.uploadFailed");
            }
        }
        return "error|" + localeMessageSourceUtil.getMessage("site.prompt.text.fileNotExist");
    }

    @GetMapping("/about")
    public String about(Model model) {
        String pageTitle = localeMessageSourceUtil.getMessage("site.page.about");
        model.addAttribute("pageTitle", pageTitle);
        return render("/about");
    }

    @GetMapping("/donate")
    public String donate(Model model) {
        String pageTitle = localeMessageSourceUtil.getMessage("site.page.donate");
        model.addAttribute("pageTitle", pageTitle);
        return render("/donate");
    }

    @GetMapping("/apidoc")
    public String apidoc(Model model) {
        String pageTitle = localeMessageSourceUtil.getMessage("site.page.api");
        model.addAttribute("pageTitle", pageTitle);
        return render("/api");
    }

    //    @GetMapping("ranking")
    public String ranking(Model model) {
        // TODO
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.integralRanking"));
        return render("/ranking");
    }

    @GetMapping("changeLanguage")
    public String changeLanguage(String lang, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if ("zh".equals(lang)) {
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh", "CN"));
        } else if ("en".equals(lang)) {
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US"));
        }
        return redirect(response, referer);
    }

}
