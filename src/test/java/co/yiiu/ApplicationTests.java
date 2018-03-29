package co.yiiu;

import co.yiiu.core.util.security.Base64Helper;
import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTests {

  @Test
  public void contextLoads() {
  }

  @Test
  public void test() {
    String url = "access_token=2d9704569cd2bfcbacd786cbe1724a0ce91aab76&scope=user&token_type=bearer";
    String token = "2225a15f-8526-4a55-b84f-5a9c6f270090";
    System.out.println(Base64Helper.encode(token.getBytes()));
  }

}
