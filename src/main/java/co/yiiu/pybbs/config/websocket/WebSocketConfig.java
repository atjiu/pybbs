package co.yiiu.pybbs.config.websocket;

import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.util.Constants;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class WebSocketConfig {

  private Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

  @Autowired
  private SystemConfigService systemConfigService;
  @Autowired
  private BindDataListener bindDataListener;

  private SocketIOServer server;

  @PostConstruct
  @DependsOn("mybatisPlusConfig")
  public void init() {
    // 如果开启了socket，则启动socket服务
    boolean websocket = systemConfigService.selectAllConfig().get("websocket").toString().equals("1");
    if (websocket) {
      com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
      config.setHostname(systemConfigService.selectAllConfig().get("websocket_host").toString());
      config.setPort(Integer.parseInt(systemConfigService.selectAllConfig().get("websocket_port").toString()));
      server = new SocketIOServer(config);

      // 添加监听
      server.addConnectListener(client -> log.info("有新用户连接, SessionId: {}", client.getSessionId()));

      server.addDisconnectListener(client -> {
        String username = Constants.websocketUserMap.get(client.getSessionId().toString()).getUsername();
        Integer userId = Constants.websocketUserMap.get(client.getSessionId().toString()).getUserId();
        log.info("用户: {} 断开了连接", username);
        Constants.usernameSocketIdMap.remove(userId);
        Constants.websocketUserMap.remove(client.getSessionId().toString());
      });
      // 添加绑定本地用户与websocket用户的监听器
      server.addEventListener("bind", Map.class, bindDataListener);

      server.start();
    }
  }

  // 关闭服务
  @PreDestroy
  public void destroy() {
    if (server != null) {
      server.stop();
    }
  }
}
