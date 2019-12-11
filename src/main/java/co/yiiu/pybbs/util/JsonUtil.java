package co.yiiu.pybbs.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.util.StringUtils;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class JsonUtil {

    // 对象转json
    public static String objectToJson(Object object) {
        if (object == null) return null;
        return JSON.toJSONString(object);
    }

    // json转对象
    public static <T> T jsonToObject(String json, Class<T> object) {
        if (StringUtils.isEmpty(json)) return null;
        return JSON.parseObject(json, object);
    }

    // 带泛型的json转对象
    public static <T> T jsonToObject(String json, TypeReference<T> type) {
        if (StringUtils.isEmpty(json)) return null;
        return JSON.parseObject(json, type.getType());
    }
}
