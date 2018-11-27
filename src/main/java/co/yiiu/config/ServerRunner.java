package co.yiiu.config;

import co.yiiu.config.properties.SiteConfig;
import co.yiiu.module.es.service.TagSearchService;
import co.yiiu.module.es.service.TopicSearchService;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya at 2018/8/17
 */
@Component
@Order(1)
public class ServerRunner implements CommandLineRunner {

  @Autowired
  private SocketIOServer server;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private TopicSearchService topicSearchService;
  @Autowired
  private TagSearchService tagSearchService;

  @Override
  public void run(String... args) {
    // 配置文件里开启了socket就启动服务，没有开启就不启动
    if (siteConfig.isSocketNotification()) server.start();
    if (siteConfig.isSearch()) {
      topicSearchService.indexedAll();
      tagSearchService.indexedAll();
    }
  }
}
