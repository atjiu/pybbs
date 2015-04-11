package cn.jfinalbbs.utils;

import com.jfinal.kit.StrKit;

import java.util.UUID;

/**
 * Created by liuyang on 15/4/2.
 */
public class StrUtil extends StrKit {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

}
