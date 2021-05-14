package com.doraemon.wish.pack.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    public static String getFormatTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault());
        return timeFormat.format(date);
    }
}
