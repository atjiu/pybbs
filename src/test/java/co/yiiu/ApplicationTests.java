package co.yiiu;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTests {

  @Test
  public void test() {
    System.out.println(new BCryptPasswordEncoder().encode("123123"));
  }

}
