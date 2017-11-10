package co.yiiu.config;

import co.yiiu.core.util.StrUtil;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.service.RoleService;
import co.yiiu.module.user.model.GithubUser;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.GithubUserService;
import co.yiiu.module.user.service.PersistentTokenService;
import co.yiiu.module.user.service.UserService;
import co.yiiu.web.secrity.YiiuUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class BeanConfig {

  @Autowired
  private PersistentTokenService persistentTokenService;
  @Autowired
  private YiiuUserDetailService yiiuUserDetailService;
  @Autowired
  private GithubUserService githubUserService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private Environment env;

  @Bean
  public PrincipalExtractor principalExtractor() {
    return map -> {
      String login = map.get("login").toString();//github的登录名
      GithubUser githubUser = githubUserService.findByLogin(login);
      User user;
      if (githubUser == null) {
        githubUser = new GithubUser();
        githubUser = githubUserService.convert(map, githubUser);
        //创建一个本地用户
        user = userService.findByUsername(login);
        if (user == null) {
          user = new User();
          user.setUsername(login);
        } else {
          user.setUsername(login + "_" + githubUser.getGithubId());
        }
        user.setEmail(githubUser.getEmail());
        user.setBio(githubUser.getBio());
        user.setUrl(githubUser.getHtml_url());
        user.setPassword(new BCryptPasswordEncoder().encode(StrUtil.randomString(16)));
        user.setInTime(new Date());
        user.setBlock(false);
        user.setToken(UUID.randomUUID().toString());
        user.setAvatar(githubUser.getAvatar_url());
        user.setAttempts(0);
        user.setScore(siteConfig.getScore());
        user.setSpaceSize(siteConfig.getUserUploadSpaceSize());
        user.setGithubUser(githubUser);

        // set user's role
        Role role = roleService.findById(3); // normal user
        Set roles = new HashSet();
        roles.add(role);
        user.setRoles(roles);
        userService.save(user);
      } else {
        githubUser = githubUserService.convert(map, githubUser);
        user = githubUser.getUser();
        githubUserService.save(githubUser);
      }
      //加载用户的权限信息
      return yiiuUserDetailService.loadUserByUsername(user.getUsername());
    };
  }

  @Bean
  public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
    PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices("remember-me"
        , yiiuUserDetailService, persistentTokenService);
    services.setAlwaysRemember(true);
    return services;
  }

  @Bean
  public EmbeddedServletContainerFactory servletContainerFactory() {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
    tomcat.addConnectorCustomizers(connector -> {
      connector.setScheme("http");
      connector.setPort(Integer.parseInt(env.getProperty("server.port")));
      if(siteConfig.isSsl()) {
        connector.setRedirectPort(8443);
        connector.setSecure(true);
      }
    });
    return tomcat;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer properties() {
    PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
    YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
    yaml.setResources(new ClassPathResource("data.yml"));
    configurer.setProperties(yaml.getObject());
    return configurer;
  }

}
