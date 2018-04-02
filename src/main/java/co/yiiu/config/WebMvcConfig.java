package co.yiiu.config;

import co.yiiu.web.interceptor.AdminInterceptor;
import co.yiiu.web.interceptor.CommonInterceptor;
import co.yiiu.web.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private CommonInterceptor commonInterceptor;
  @Autowired
  private UserInterceptor userInterceptor;
  @Autowired
  private AdminInterceptor adminInterceptor;

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.setUseSuffixPatternMatch(false);
  }

  /**
   * Add intercepter
   *
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(commonInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/common/**", "/admin/**");
    registry.addInterceptor(userInterceptor).addPathPatterns(
        "/topic/create",
        "/topic/save",
        "/topic/*/edit",
        "/comment/save",
        "/collect/*/add",
        "/collect/*/delete",
        "/notification/list",
        "/user/setting/*"
    );
    registry.addInterceptor(adminInterceptor)
        .addPathPatterns("/admin/**")
        .excludePathPatterns("/admin/login", "/admin/logout");
  }
}
