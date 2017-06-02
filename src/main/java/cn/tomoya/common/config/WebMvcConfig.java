package cn.tomoya.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.tomoya.interceptor.CommonInterceptor;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Configuration
@ImportResource("classpath:spring-captcha.xml")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private CommonInterceptor commonInterceptor;

  /**
   * Add intercepter
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    super.addInterceptors(registry);
    registry.addInterceptor(commonInterceptor).addPathPatterns("/**").excludePathPatterns("/api/**");
  }

}
