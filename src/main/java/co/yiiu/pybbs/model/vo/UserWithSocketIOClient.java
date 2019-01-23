package co.yiiu.pybbs.model.vo;

import com.corundumstudio.socketio.SocketIOClient;

import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class UserWithSocketIOClient implements Serializable {

  private String username;
  private Integer userId;
  private SocketIOClient client;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public SocketIOClient getClient() {
    return client;
  }

  public void setClient(SocketIOClient client) {
    this.client = client;
  }
}
