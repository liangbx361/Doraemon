package com.wish.doraemon.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapperUtil {
    private static final ObjectMapper OBJECT_MAPPER;

    public JsonObjectMapperUtil() {
    }

    public static ObjectMapper getMapperInstance() {
        return OBJECT_MAPPER;
    }

    public static JavaType constructParametricType(Class<?> collectionClass, Class... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    static {
        OBJECT_MAPPER = (new ObjectMapper())
                //允许遇到未知属性时不失败
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                //允许空字符串转空对象
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }
}
