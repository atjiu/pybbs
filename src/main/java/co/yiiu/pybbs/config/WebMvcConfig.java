package co.yiiu.pybbs.config;

import co.yiiu.pybbs.interceptor.CommonInterceptor;
import co.yiiu.pybbs.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

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
  protected void addCorsMappings(CorsRegistry registry) {
    super.addCorsMappings(registry);
    registry.addMapping("/api/**")
        .allowedHeaders("*")
        .allowedMethods("*")
        .allowedOrigins("*")
        .allowCredentials(false);
  }

  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    // 配置全局日志拦截器，用于记录用户的请求记录
    registry.addInterceptor(commonInterceptor)
        .addPathPatterns("/**");
    // 用户拦截器，拦截用户是否登录
    registry.addInterceptor(userInterceptor)
        .addPathPatterns(
            "/settings",
            "/topic/create",
            "/topic/edit/*",
            "/api/upload" // 上传图片路由
        );
  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/", "file:./static/");
  }

  // 配置网站默认语言
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
    return sessionLocaleResolver;
  }

  // 解析put delete请求时带的参数
//  @Bean
//  public FormContentFilter formContentFilter() {
//    return new FormContentFilter();
//  }

}
