package co.yiiu.config;

import co.yiiu.config.properties.SiteConfig;
import co.yiiu.core.bean.Message;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomoya at 2018/8/17
 */
@org.springframework.context.annotation.Configuration
public class SocketConfig {

  @Autowired
  private SiteConfig siteConfig;

  @Bean
  public SocketIOServer socketIOServer() {
    Configuration config = new Configuration();
    config.setHostname(siteConfig.getSocket().getHostname());
    config.setPort(siteConfig.getSocket().getPort());

    // 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
//    config.setUpgradeTimeout(10000);

    // Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
//    config.setPingInterval(25000);

    // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
//    config.setPingTimeout(60000);

    // 握手协议参数使用JWT的Token认证方案
//    config.setAuthorizationListener(data -> {
      // 可以使用如下代码获取用户密码信息
//      String token = data.getSingleUrlParam("token");
//      return true;
//    });

    return new SocketIOServer(config);
  }

  @Bean
  public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
    return new SpringAnnotationScanner(socketServer);
  }

}