package co.yiiu.pybbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PybbsApplication {

  public static void main(String[] args) {
    SpringApplication.run(PybbsApplication.class, args);
  }
}
