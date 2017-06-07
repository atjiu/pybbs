package cn.tomoya.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import cn.tomoya.module.security.core.MyFilterSecurityInterceptor;
import cn.tomoya.module.security.core.MyUserDetailService;
import cn.tomoya.module.security.core.ValidateCodeAuthenticationFilter;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private MyUserDetailService myUserDetailService;
  @Autowired
  private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
  @Autowired
  private ValidateCodeAuthenticationFilter validateCodeAuthenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/static/**")
        .permitAll()
        .antMatchers("/admin/**", "/topic/create", "/topic/*/delete", "/topic/*/edit", "/reply/save", "/reply/*/delete",
            "/reply/*/edit", "/reply/*/up", "/reply/*/cancelUp", "/reply/*/down", "/reply/*/cancelDown", "/collect/**",
            "/notification/**", "/user/setting", "/user/changePassword", "/user/refreshToken")
        .authenticated();

    http.formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .failureUrl("/login?error")
        .defaultSuccessUrl("/")
        .permitAll();

    // token expired after 30 days
    http.rememberMe().key("remember-me").alwaysRemember(true).tokenValiditySeconds(2592000);

    http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/")
        .deleteCookies("remember-me");

    http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    http.addFilterBefore(validateCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    http.csrf().ignoringAntMatchers("/api/**");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Bean
  public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
    SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
    auth.setTargetUrlParameter("targetUrl");
    return auth;
  }
}
