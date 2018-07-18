package co.yiiu.core.util;

/**
 * 判断数据是否在枚举里
 * Created by tomoya at 2018/4/10
 */
public class EnumUtil {

  public static boolean isDefined(Enum[] enums, String value) {
    for (Enum e: enums) {
      if(e.name().equals(value)) {
        return true;
      }
    }
    return false;
  }

}
