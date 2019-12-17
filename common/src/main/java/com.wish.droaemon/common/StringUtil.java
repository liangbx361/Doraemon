package com.wish.droaemon.common;

/**
 * Title:
 * Description:
 * Copyright © 2001-2019 17173. All rights reserved.
 *
 * @author liangbx
 * @version 2019/3/12
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
