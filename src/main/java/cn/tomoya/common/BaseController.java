package cn.tomoya.common;

import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class BaseController {

    Logger log = Logger.getLogger(BaseController.class);

    @Autowired
    private SettingService settingService;
    @Autowired
    private UserService userService;

    /**
     * redirect with params
     *
     * @param path
     * @return
     */
    protected String redirect(String path) {
        return "redirect:" + path;
    }

    /**
     * redirect with no params
     *
     * @param response
     * @param path
     * @return
     */
    protected String redirect(HttpServletResponse response, String path) {
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * render page
     *
     * @param path
     * @return
     */
    protected String render(String path) {
        return settingService.getTheme() + path;
    }

    protected String renderText(HttpServletResponse response, String msg) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get security user
     *
     * @return
     */
    protected UserDetails getSecurityUser() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean b = o instanceof UserDetails;
        if (b) {
            log.info(o.toString());
            return (UserDetails) o;
        }
        return null;
    }

    /**
     * get user info
     *
     * @return
     */
    protected User getUser() {
        UserDetails userDetails = getSecurityUser();
        if (userDetails != null) {
            return userService.findByUsername(userDetails.getUsername());
        }
        return null;
    }

}
