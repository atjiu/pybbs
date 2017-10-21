package co.yiiu.config;

import co.yiiu.module.user.service.PersistentTokenService;
import co.yiiu.web.secrity.ValidateCodeAuthenticationFilter;
import co.yiiu.web.secrity.YiiuFilterSecurityInterceptor;
import co.yiiu.web.secrity.YiiuUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private YiiuUserDetailService yiiuUserDetailService;
  @Autowired
  private YiiuFilterSecurityInterceptor yiiuFilterSecurityInterceptor;
  @Autowired
  private ValidateCodeAuthenticationFilter validateCodeAuthenticationFilter;

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired
  private PersistentTokenService persistentTokenService;

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
            "/user/changePassword",
            "/user/accessToken",
            "/user/refreshToken",
            "/user/space",
            "/space/deleteFile"
        )
        .authenticated();

    http.formLogin()
        .successHandler(savedRequestAwareAuthenticationSuccessHandler())
        .loginPage(siteConfig.getBaseUrl() + "/login")
        .loginProcessingUrl(siteConfig.getBaseUrl() + "/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .failureUrl(siteConfig.getBaseUrl() + "/login?error")
        .defaultSuccessUrl(siteConfig.getBaseUrl() + "/")
        .permitAll();

    http.rememberMe().key("remember-me").rememberMeServices(persistentTokenBasedRememberMeServices());

    http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl(siteConfig.getBaseUrl() + "/")
        .deleteCookies("JSESSIONID");

    http.addFilterBefore(yiiuFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    http.addFilterBefore(validateCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    http.csrf().ignoringAntMatchers("/upload", "/user/space/deleteFile");
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

  @Bean
  public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
    SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
    auth.setTargetUrlParameter("targetUrl");
    return auth;
  }

  @Bean
  public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
    PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices("remember-me"
            ,userDetailsService(),persistentTokenService);
    services.setAlwaysRemember(true);
    return services;
  }
}
