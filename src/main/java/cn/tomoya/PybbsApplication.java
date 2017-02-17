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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@EnableCaching
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
     * add interceptors
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
     * configuration security
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
                            "/reply/*/up",
                            "/reply/*/cancelUp",
                            "/reply/*/down",
                            "/reply/*/cancelDown",
                            "/collect/**",
                            "/notification/**",
                            "/user/setting",
                            "/user/changePassword"
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

    // set defaut locale en_US
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        if(siteConfig.getI18n().equals("en")) {
            slr.setDefaultLocale(Locale.US);
        } else if(siteConfig.getI18n().equals("zh")) {
            slr.setDefaultLocale(Locale.CHINA);
        } else {
            slr.setDefaultLocale(Locale.US);
        }
        return slr;
    }

    public static void main(String[] args) {
        SpringApplication.run(PybbsApplication.class, args);
    }
}
