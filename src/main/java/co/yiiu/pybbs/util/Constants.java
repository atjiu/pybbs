package co.yiiu.pybbs.util;

import co.yiiu.pybbs.model.vo.UserWithSocketIOClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class Constants {

  private Constants() {}

  public static final String REDIS_SYSTEM_CONFIG_KEY = "pybbs_system_config";

  public static final String REDIS_TOPIC_KEY = "pybbs_topic_"; // 后面还要拼上话题的id

  public static final String REDIS_COMMENTS_KEY = "pybbs_comments_"; // 后面还要拼上话题的id

  public static final String REDIS_USER_USERNAME_KEY = "pybbs_user_username_"; // 后面还要拼上用户名
  public static final String REDIS_USER_TOKEN_KEY = "pybbs_user_token_"; // 后面还要拼上用户的token
  public static final String REDIS_USER_ID_KEY = "pybbs_user_id_"; // 后面还要拼上用户的id

  public static final String REDIS_TOPIC_VIEW_IP_ID_KEY = "pybbs_topic_view_ip_%s_topic_%s"; // 需要格式化字符串填充上ip地址跟话题id

  // 如果没有开启redis服务，但开启了websocket，那么连接的用户信息会被存在这个对象里
  public static final ConcurrentHashMap<String, UserWithSocketIOClient> websocketUserMap = new ConcurrentHashMap<>();
  public static final ConcurrentHashMap<Integer, String> usernameSocketIdMap = new ConcurrentHashMap<>();

}
