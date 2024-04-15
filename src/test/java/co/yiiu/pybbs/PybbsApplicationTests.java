package co.yiiu.pybbs;

import co.yiiu.pybbs.plugin.ElasticSearchService;
import co.yiiu.pybbs.util.MyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PybbsApplicationTests {

    @Resource
    ElasticSearchService elasticSearchService;

    @Test
    public void contextLoads() {
        MyPage<Map<String, Object>> mapMyPage = elasticSearchService.searchDocument(1, 20, "你好", "title");
        for (Map<String, Object> record : mapMyPage.getRecords()) {
            System.out.println(record.toString());
        }
    }

}
