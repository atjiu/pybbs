package co.yiiu.pybbs.util;

import com.google.gson.Gson;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class JsonUtil {

  public final static Gson gson = new Gson();

  // 对象转json
  public static String objectToJson(Object object) {
    return gson.toJson(object);
  }

  // json转对象
  public static <T> T jsonToObject(String json, Class<T> object) {
    return gson.fromJson(json, object);
  }
}
