package com.doraemon.wish.pack.util;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {

    public static String getApkUrl(HttpServletRequest request, Long gameId, String apk) {
        return getBaseUrl(request) + gameId + "/" + apk;
    }

    public static String getBaseUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/doraemon/";
    }
}
