package co.yiiu.core.exception;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by tomoya at 2018/3/15
 */
public class ApiAssert extends Assert {

  public static void isNull(Object object, String message) {
    if (object != null) {
      throw new ApiException(message);
    }
  }

  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new ApiException(message);
    }
  }

  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new ApiException(message);
    }
  }

  public static void notTrue(boolean expression, String message) {
    if (expression) {
      throw new ApiException(message);
    }
  }

  public static void isEmpty(String txt, String message) {
    if(!StringUtils.isEmpty(txt)) {
      throw new ApiException(message);
    }
  }

  public static void notEmpty(String txt, String message) {
    if(StringUtils.isEmpty(txt)) {
      throw new ApiException(message);
    }
  }
}
