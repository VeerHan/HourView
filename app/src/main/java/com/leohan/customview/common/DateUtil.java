package com.leohan.customview.common;

public class DateUtil {
    public static float convert(String time) {
        return Float.valueOf(time.replace(":", ""));
    }
}
