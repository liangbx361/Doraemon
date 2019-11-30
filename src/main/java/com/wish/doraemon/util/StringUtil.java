package com.wish.doraemon.util;

import org.springframework.lang.Nullable;

/**
 * Title:
 * Description:
 * Copyright © 2001-2019 17173. All rights reserved.
 *
 * @author liangbx
 * @version 2019/3/12
 */
public class StringUtil {

    public static boolean isEmpty(@Nullable String str) {
        return str == null || str.equals("");
    }

    public static boolean isNotEmpty(@Nullable String str) {
        return !isEmpty(str);
    }
}
