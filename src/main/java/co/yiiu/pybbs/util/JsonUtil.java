package co.yiiu.pybbs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class JsonUtil {

    private final static Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> jsonToListObject(String json, Class<T> clazz) {
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            return objectMapper.readValue(json, typeFactory.constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            log.error("json to list error: {}", e.getMessage());
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("json to object error: {}", e.getMessage());
            return null;
        }
    }

    public static String objectToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("object to json string error: {}", e.getMessage());
            return null;
        }
    }
}
