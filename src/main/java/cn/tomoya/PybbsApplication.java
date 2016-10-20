package cn.tomoya;

import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.interceptor.CommonInterceptor;
import cn.tomoya.module.security.core.MyFilterSecurityInterceptor;
import cn.tomoya.module.security.core.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@SpringBootApplication
@EnableConfigurationProperties(SiteConfig.class)
public class PybbsApplication extends WebMvcConfigurerAdapter {

    @Autowired
    private CommonInterceptor commonInterceptor;
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    private SiteConfig siteConfig;

    /**
     * 添加viewController
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/donate").setViewName(siteConfig.getTheme() + "/donate");
        registry.addViewController("/about").setViewName(siteConfig.getTheme() + "/about");
        registry.addViewController("/apidoc").setViewName(siteConfig.getTheme() + "/api");
    }

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");
    }

    /**
     * 配置权限
     */
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers(
                            "/admin/**",
                            "/topic/create",
                            "/topic/*/delete",
                            "/topic/*/edit",
                            "/reply/save",
                            "/reply/*/delete",
                            "/reply/*/edit",
                            "/collect/**",
                            "/notification/**"
                    ).authenticated();
            http
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureUrl("/login?error")
                    .defaultSuccessUrl("/")
                    .permitAll();
            http
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/");
            http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(PybbsApplication.class, args);
    }
}
