package co.yiiu;

import co.yiiu.core.util.security.crypto.BCryptPasswordEncoder;
import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTests {

  @Test
  public void contextLoads() {
  }

  @Test
  public void test() {
    System.out.println(new BCryptPasswordEncoder().encode("123123"));
  }

}
