package co.yiiu.pybbs.conf;

import co.yiiu.pybbs.conf.properties.SiteConfig;
import co.yiiu.pybbs.filters.CommonFilter;
import co.yiiu.pybbs.filters.JwtFilter;
import co.yiiu.pybbs.filters.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by tomoya at 2018/9/3
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  @Autowired
  private JwtFilter jwtFilter;
  @Autowired
  private CommonFilter commonFilter;
  @Autowired
  private UserFilter userFilter;
  @Autowired
  private SiteConfig siteConfig;

  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    super.addInterceptors(registry);
    // 全局拦截器，统计每个请求执行的时间
    registry.addInterceptor(commonFilter).addPathPatterns("/**");
    // JWT拦截器，验证token
    registry.addInterceptor(jwtFilter)
        .addPathPatterns(
            "/topic/create",
            "/topic/update",
            "/topic/delete",
            "/topic/good",
            "/topic/top",
            "/comment/create",
            "/comment/update",
            "/comment/delete",
            "/user/settings/**",
            "/collect/save",
            "/collect/delete",
            "/upload"
        );
    registry.addInterceptor(userFilter)
        .addPathPatterns(
            "/topic/create",
            "/topic/update",
            "/topic/delete",
            "/comment/create",
            "/comment/update",
            "/comment/delete",
            "/upload"
        );
  }

  @Override
  protected void addCorsMappings(CorsRegistry registry) {
    super.addCorsMappings(registry);
    registry.addMapping("/**")
        .allowedHeaders("*")
        .allowedMethods("*")
        .allowedOrigins(siteConfig.getCorsDomain().toArray(new String[siteConfig.getCorsDomain().size()]));
  }
}
