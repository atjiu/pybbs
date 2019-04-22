package co.yiiu.pybbs;

import co.yiiu.pybbs.util.identicon.Identicon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PybbsApplicationTests {

  @Autowired
  Identicon identicon;

  @Test
  public void contextLoads() {

    identicon.generator("殷雷雷");
    identicon.generator("maodou0217");
    identicon.generator("abc");
    identicon.generator("mywaya");
    identicon.generator("tomoya");
  }

}
