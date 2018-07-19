package co.yiiu;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTests {

  @Test
  public void contextLoads() {
  }

  @Test
  public void test() throws IOException, InterruptedException {
    System.out.println(new BCryptPasswordEncoder().encode("123123"));
  }

}
