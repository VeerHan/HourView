package com.leohan.hourview;

public class DateUtil {
    public static float convert(String time) {
        return Float.valueOf(time.replace(":", ""));
    }
}
