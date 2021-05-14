package com.doraemon.wish.pack.util;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {

    public static String getApkUrl(HttpServletRequest request, String path, String apk) {
        return getBaseUrl(request) + path + "/" + apk;
    }

    public static String getBaseUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/doraemon/";
    }
}
