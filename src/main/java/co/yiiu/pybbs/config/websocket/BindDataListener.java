package co.yiiu.pybbs.config.websocket;

import co.yiiu.pybbs.model.vo.UserWithSocketIOClient;
import co.yiiu.pybbs.service.NotificationService;
import co.yiiu.pybbs.util.Constants;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class BindDataListener implements DataListener<Map> {

  private Logger log = LoggerFactory.getLogger(BindDataListener.class);

  @Autowired
  private NotificationService notificationService;

  @Override
  public void onData(SocketIOClient client, Map data, AckRequest ackSender) {
    if (data == null || data.get("username") == null || data.get("userId") == null) return;
    Integer userId = Integer.parseInt(data.get("userId").toString());
    String username = data.get("username").toString();
    String socketId = client.getSessionId().toString();
    log.info("用户: {} 与 SocketId: {} 绑定成功", username, socketId);
    // 封装对象
    UserWithSocketIOClient userWithSocketIOClient = new UserWithSocketIOClient();
    userWithSocketIOClient.setClient(client);
    userWithSocketIOClient.setUserId(userId);
    userWithSocketIOClient.setUsername(username);
    Constants.websocketUserMap.put(socketId, userWithSocketIOClient);
    // 绑定username与socketId并存到另一个map里，方便后面发消息时查询
    Constants.usernameSocketIdMap.put(userId, socketId);

    // 查询当前连接用户的未读消息
    long countNotRead = notificationService.countNotRead(userId);
    if (countNotRead > 0) {
      client.sendEvent("notification_notread", countNotRead);
    }
  }
}
