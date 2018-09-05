package co.yiiu.pybbs.conf;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya at 2018/9/3
 */
@Component
public class BeanConfig {

  @Bean
  public static PropertySourcesPlaceholderConfigurer properties() {
    PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
    YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
    yaml.setResources(new FileSystemResource("conf.yml"));
    configurer.setProperties(yaml.getObject());
    return configurer;
  }

}
