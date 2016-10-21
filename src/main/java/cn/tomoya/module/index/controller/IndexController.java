package cn.tomoya.module.index.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.module.topic.elastic.ElasticTopicService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import cn.tomoya.util.JsonUtil;
import cn.tomoya.util.identicon.Identicon;
import com.github.javautils.Constants;
import com.github.javautils.encrypt.EncryptionUtil;
import com.github.javautils.string.StringUtil;
import com.github.javautils.web.CookieUtils;
import lombok.extern.log4j.Log4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@Log4j
public class IndexController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private Identicon identicon;
    @Autowired
    private ElasticTopicService elasticTopicService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index(String tab, Integer p, Model model) {
        String sectionName = tab;
        if(StringUtil.isBlank(tab)) tab = "全部";
        if(tab.equals("全部") || tab.equals("精华") || tab.equals("等待回复")) {
            sectionName = "版块";
        }
        Page<Topic> page = topicService.page(p == null ? 1 : p, siteConfig.getPageSize(), tab);
        model.addAttribute("page", page);
        model.addAttribute("tab", tab);
        model.addAttribute("sectionName", sectionName);
        model.addAttribute("user", getUser());
        return render("/index");
    }

    /**
     * 进入登录页
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(String s, Model model, HttpServletResponse response) {
        if (getUser() != null) {
            return redirect(response, "/");
        }
        model.addAttribute("s", s);
        return render("/login");
    }

    /**
     * 进入注册页面
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toRegister(HttpServletResponse response) {
        if (getUser() != null) {
            return redirect(response, "/");
        }
        return render("/register");
    }

    /**
     * 注册验证
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username, String password, HttpServletResponse response, Model model) {
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("errors", "用户名已经被注册");
        } else if (StringUtil.isBlank(username)) {
            model.addAttribute("errors", "用户名不能为空");
        } else if (StringUtil.isBlank(password)) {
            model.addAttribute("errors", "密码不能为空");
        } else {
            String avatarName = StringUtil.getUUID();
            identicon.generator(avatarName);
            user = new User();
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setInTime(new Date());
            user.setAvatar(siteConfig.getBaseUrl() + "static/images/avatar/" + avatarName + ".png");
            userService.save(user);
            return redirect(response, "/login?s=reg");
        }
        return render("/register");
    }

    /**
     * 上传
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if(!file.isEmpty()) {
            try {
                String type = file.getContentType();
                String suffix = "." + type.split("/")[1];
                String fileName = StringUtil.getUUID() + suffix;
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(siteConfig.getUploadPath() + fileName)));
                stream.write(bytes);
                stream.close();
                return JsonUtil.success(siteConfig.getBaseUrl() + "static/images/upload/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JsonUtil.error("上传失败");
    }

    /**
     * 搜索
     * @param p
     * @param q
     * @param model
     * @return
     */
    @RequestMapping("/search")
    public String search(Integer p, String q, Model model) {
        model.addAttribute("q", q);
        model.addAttribute("page", elasticTopicService.pageByKeyword(p == null ? 1 : p, siteConfig.getPageSize(), q));
        return render("/search");
    }

}
