package co.yiiu.pybbs.util;

import co.yiiu.pybbs.service.ISystemConfigService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://atjiu.github.io
 */
@Component
public class CookieUtil {

    @Resource
    private ISystemConfigService systemConfigService;

    public void setCookie(String key, String value) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        ResponseCookie responseCookie = ResponseCookie.from(key, value)
                // 设置 HttpOnly
                .httpOnly(true)
                // 设置 SameSite 为 Strict，不依赖浏览器默认行为
                .sameSite("Strict")
                // 设置 Cookie 路径为网站根路径
                .path("/")
                // 设置过期时间（单位：秒）
                .maxAge(Integer.parseInt(systemConfigService.selectAllConfig().get("cookie_max_age")))
                // 为 https 访问开启 secure
                .secure(request.isSecure())
                .build();
        assert response != null;
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }


    public String getCookie(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equalsIgnoreCase(name)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    // 清除cookie
    public void clearCookie(String name) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getResponse();
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        cookie.setDomain(systemConfigService.selectAllConfig().get("cookie_domain"));
        cookie.setPath("/");
        assert response != null;
        response.addCookie(cookie);
    }
}
