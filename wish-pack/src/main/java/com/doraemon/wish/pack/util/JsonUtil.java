package com.doraemon.wish.pack.util;

import com.droaemon.common.util.FileUtil;
import com.droaemon.common.util.JsonMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonUtil {

    public static void modelToJsonFile(Object modle, String path, String fileName) throws JsonProcessingException {
        String modelJson = JsonMapperUtil.objectToString(modle);
        FileUtil.save(path, fileName, modelJson.getBytes());
    }
}
