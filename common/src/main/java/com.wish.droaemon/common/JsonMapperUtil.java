package com.wish.droaemon.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonMapperUtil {

    public static String objectToString(Object model) throws JsonProcessingException {
        if (model == null) {
            throw new IllegalArgumentException("model not be null");
        }
        return JsonObjectMapperUtil.getMapperInstance().writeValueAsString(model);
    }

    public static <T> T stringToObject(String data, Class<T> clazz) throws IOException {
        if (StringUtil.isEmpty(data)) {
            throw new IllegalArgumentException("data not be null");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data, clazz);
    }

    public static <T> List<T> stringToList(String data, Class<T> clazz) throws IOException {
        if (StringUtil.isEmpty(data)) {
            throw new IllegalArgumentException("data not be null");
        }
        JavaType type = JsonObjectMapperUtil.constructParametricType(ArrayList.class, clazz);
        return JsonObjectMapperUtil.getMapperInstance().readValue(data, type);
    }

}
