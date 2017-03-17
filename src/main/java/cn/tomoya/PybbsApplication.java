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
     * 添加viewController
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/about").setViewName(siteConfig.getTheme() + "/about");
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
                            "/reply/*/up",
                            "/reply/*/cancelUp",
                            "/reply/*/down",
                            "/reply/*/cancelDown",
                            "/collect/**",
                            "/notification/**",
                            "/user/setting",
                            "/user/changePassword",
                            "/user/refreshToken"
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
            http.csrf().ignoringAntMatchers("/api/**");
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
        }
    }

    public static void main(String[] args) {
        for(String s : args) {
            String[] _ss = s.split("=");
            if(_ss[0].equals("port")) {
                System.setProperty("server.port", _ss[1]);
            } else if(_ss[0].equals("cache")) {
                System.setProperty("spring.freemarker.cache", _ss[1]);
            } else if(_ss[0].equals("password")) {
                System.setProperty("spring.datasource.password", _ss[1]);
            } else if(_ss[0].equals("showSql")) {
                System.setProperty("spring.jpa.show-sql", _ss[1]);
            } else if(_ss[0].equals("name")) {
                System.setProperty("site.name", _ss[1]);
            } else if(_ss[0].equals("intro")) {
                System.setProperty("site.intro", _ss[1]);
            } else if(_ss[0].equals("baseUrl")) {
                System.setProperty("site.baseUrl", _ss[1]);
            } else if(_ss[0].equals("staticUrl")) {
                System.setProperty("site.staticUrl", _ss[1]);
            } else if(_ss[0].equals("pageSize")) {
                System.setProperty("site.pageSize", _ss[1]);
            } else if(_ss[0].equals("uploadPath")) {
                System.setProperty("site.uploadPath", _ss[1]);
            } else if(_ss[0].equals("editor")) {
                System.setProperty("site.editor", _ss[1]);
            }
        }
        SpringApplication.run(PybbsApplication.class, args);
    }
}
