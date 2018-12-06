package co.yiiu.pybbs.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.yiiu.pybbs.config.realm.MyCredentialsMatcher;
import co.yiiu.pybbs.config.realm.MyShiroRealm;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class ShiroConfig {

  private Logger log = LoggerFactory.getLogger(ShiroConfig.class);

  @Autowired
  private MyShiroRealm myShiroRealm;

  @Bean
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    log.info("开始配置shiroFilter...");
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    //拦截器.
    Map<String,String> map = new HashMap<>();
    // 配置不会被拦截的链接 顺序判断  相关静态资源
    map.put("/static/**", "anon");

    //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
    map.put("/admin/logout", "logout");

    //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;

    //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
    map.put("/admin/**", "authc");
    // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
    shiroFilterFactoryBean.setLoginUrl("/adminlogin");
    // 登录成功后要跳转的链接
    shiroFilterFactoryBean.setSuccessUrl("/admin/index");

    //未授权界面;
    shiroFilterFactoryBean.setUnauthorizedUrl("/error");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
    return shiroFilterFactoryBean;
  }

  // 配置加密方式
  // 配置了一下，这货就是验证不过，，改成手动验证算了，以后换加密方式也方便
  @Bean
  public MyCredentialsMatcher myCredentialsMatcher() {
    return new MyCredentialsMatcher();
  }

  // 安全管理器配置
  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    myShiroRealm.setCredentialsMatcher(myCredentialsMatcher());
    securityManager.setRealm(myShiroRealm);
    return securityManager;
  }

  //加入注解的使用，不加入这个注解不生效
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }

  @Bean
  @ConditionalOnMissingBean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
    defaultAAP.setProxyTargetClass(true);
    return defaultAAP;
  }

}
