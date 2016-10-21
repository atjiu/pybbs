package cn.tomoya.interceptor;

import cn.tomoya.common.config.SiteConfig;
import com.github.javautils.net.IpUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Component
@Log4j
public class CommonInterceptor implements HandlerInterceptor {

    @Autowired
    private SiteConfig siteConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long start = System.currentTimeMillis();
        request.setAttribute("_start", start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            ModelMap modelMap = modelAndView.getModelMap();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAuthenticated = false;
            if (authentication != null) {
                Object o = authentication.getPrincipal();
                if (o instanceof UserDetails) {
                    isAuthenticated = true;
                    modelMap.addAttribute("_principal", ((UserDetails) o).getUsername());
                    modelMap.addAttribute("_roles", ((UserDetails) o).getAuthorities());
                }
            }
            modelMap.addAttribute("_isAuthenticated", isAuthenticated);
            modelMap.addAttribute("baseUrl", siteConfig.getBaseUrl());
            modelMap.addAttribute("siteTitle", siteConfig.getName());
            modelMap.addAttribute("sections", siteConfig.getSections());
            modelMap.addAttribute("_search", siteConfig.isElastic());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long start = (long) request.getAttribute("_start");

        String actionName = request.getRequestURI();
        String clientIp = IpUtil.getIpAddr(request);
        StringBuffer logstring = new StringBuffer();
        logstring.append(clientIp).append("|").append(actionName).append("|");
        Map<String, String[]> parmas = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iter = parmas.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String[]> entry = iter.next();
            logstring.append(entry.getKey());
            logstring.append("=");
            for (String paramString : entry.getValue()) {
                logstring.append(paramString);
            }
            logstring.append("|");
        }
        long executionTime = System.currentTimeMillis() - start;
        logstring.append("excutetime=").append(executionTime).append("ms");
        log.info(logstring.toString());
    }
}
