package co.yiiu.config;

import co.yiiu.module.security.core.MyAuthenticationProvider;
import co.yiiu.module.security.core.MyCustomAuthenticationFilter;
import co.yiiu.module.security.core.MyFilterSecurityInterceptor;
import co.yiiu.module.security.core.MyUserDetailService;
import co.yiiu.module.user.service.PersistentTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private MyUserDetailService myUserDetailService;
  @Autowired
  private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
  @Autowired
  private MyCustomAuthenticationFilter myCustomAuthenticationFilter;
  @Autowired
  private PersistentTokenService persistentTokenService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.authorizeRequests()
        .antMatchers("/admin/**")
        .authenticated();

    http.formLogin()
        .loginPage("/adminlogin")
        .loginProcessingUrl("/adminlogin")
        .failureUrl("/adminlogin?error")
        .defaultSuccessUrl("/admin/index")
        .permitAll();

    http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
        .logoutSuccessUrl("/adminlogin")
        .deleteCookies("JSESSIONID", "remember-me");

    http.addFilterBefore(myCustomAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterAfter(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);

  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/static/**");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    MyAuthenticationProvider provider = new MyAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    provider.setUserDetailsService(myUserDetailService);
    return provider;
  }

  @Bean
  public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
    PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices("remember-me"
        , myUserDetailService, persistentTokenService);
    services.setAlwaysRemember(true);
    return services;
  }


}
