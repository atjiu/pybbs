package co.yiiu.config;

import co.yiiu.web.secrity.ValidateCodeAuthenticationFilter;
import co.yiiu.web.secrity.YiiuFilterSecurityInterceptor;
import co.yiiu.web.secrity.YiiuUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private YiiuUserDetailService yiiuUserDetailService;
  @Autowired
  private YiiuFilterSecurityInterceptor yiiuFilterSecurityInterceptor;
  @Autowired
  private BeanConfig beanConfig;
  @Autowired
  private ValidateCodeAuthenticationFilter validateCodeAuthenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/static/**")
        .permitAll()
        .antMatchers(
            "/upload",
            "/attendance",
            "/admin/**",
            "/topic/create",
            "/topic/*/delete",
            "/topic/*/edit",
            "/reply/save",
            "/reply/*/delete",
            "/reply/*/edit",
            "/reply/*/up",
            "/reply/*/cancelUp",
            "/reply/*/down",
            "/reply/*/cancelDown",
            "/collect/**",
            "/notification/**",
            "/user/profile",
            "/user/changeAvatar",
            "/user/changePassword",
            "/user/accessToken",
            "/user/refreshToken",
            "/user/space",
            "/space/deleteFile"
        )
        .authenticated();

    http.formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .failureUrl("/login?error")
        .defaultSuccessUrl("/")
        .permitAll();

    http.rememberMe().key("remember-me").rememberMeServices(beanConfig.persistentTokenBasedRememberMeServices());

    http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID", "remember-me");

    http.addFilterBefore(yiiuFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    http.addFilterBefore(validateCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    http.csrf().ignoringAntMatchers("/upload", "/user/space/deleteFile", "/github_login", "/favicon.ico");

  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/static/**");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(yiiuUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
