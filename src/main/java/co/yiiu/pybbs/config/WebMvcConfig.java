package co.yiiu.pybbs.config;

import co.yiiu.pybbs.interceptor.CommonInterceptor;
import co.yiiu.pybbs.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  @Autowired
  private CommonInterceptor commonInterceptor;
  @Autowired
  private UserInterceptor userInterceptor;

  @Override
  protected void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/notifications").setViewName("front/notifications");
    registry.addViewController("/login").setViewName("front/login");
    registry.addViewController("/register").setViewName("front/register");
    registry.addViewController("/api").setViewName("front/api");
  }

  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    // 配置全局日志拦截器
    registry.addInterceptor(commonInterceptor)
        .addPathPatterns("/**");
    registry.addInterceptor(userInterceptor)
        .addPathPatterns(
            "/settings",
            "/topic/create",
            "/topic/edit/*",
            "/api/topic/*",
            "/api/comment/*",
            "/api/collect/*",
            "/api/settings/*",
            "/api/notification/notRead"
        );
  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/", "file:./static/");
  }
}
