package co.yiiu.core.util;

import com.google.gson.Gson;

/**
 * Created by tomoya at 2018/3/27
 */
public class JsonUtil {

  public final static Gson gson = new Gson();

  public static String objectToJson(Object object) {
    return gson.toJson(object);
  }

  public static <T> T jsonToObject(String json, Class<T> object) {
    return gson.fromJson(json, object);
  }

}
