package com.wish.doraemon.user.util;

import com.wish.doraemon.user.security.jwt.JwtHandler;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {

    public static Integer getUserId(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.replace(JwtHandler.TOKEN_PREFIX, "");
        return Integer.valueOf(JwtHandler.getInstance().getUserId(token));
    }
}
