package co.yiiu.pybbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// 不用默认配置的数据源，自己配置
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    FlywayAutoConfiguration.class
})
public class PybbsApplication {

  public static void main(String[] args) {
    SpringApplication.run(PybbsApplication.class, args);
  }
}
