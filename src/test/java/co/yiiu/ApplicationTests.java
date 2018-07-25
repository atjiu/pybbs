package co.yiiu;

import co.yiiu.module.topic.mapper.TopicMapper;
import co.yiiu.module.topic.pojo.TopicWithBLOBs;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTests {

  @Autowired
  private TopicMapper topicMapper;

  @Test
  public void test() {
    long t1 = System.currentTimeMillis();
    List<Map> topic = topicMapper.findTopic(null, null, null, 1, 20, null);
    long t2 = System.currentTimeMillis();
    System.out.println(t2 - t1);
    System.out.println(topic);
  }

  @Test
  public void test1() {
    long t1 = System.currentTimeMillis();
    TopicWithBLOBs topic = new TopicWithBLOBs();
    topic.setId(1);
    topic.setView(2);
    topicMapper.updateByPrimaryKeySelective(topic);
    long t2 = System.currentTimeMillis();
    System.out.println(t2 - t1);
    System.out.println(topic);
  }

}
