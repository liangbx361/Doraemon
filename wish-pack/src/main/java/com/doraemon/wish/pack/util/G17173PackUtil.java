package com.doraemon.wish.pack.util;

import com.droaemon.common.util.FileUtil;

import java.io.IOException;

public class G17173PackUtil {

    public static boolean isModifySuccess() {
        try {
            byte[] logBytes = FileUtil.read("input.log");
            String log = new String(logBytes);
            return log.contains("modify success");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
