package com.leohan.customview.common;

import java.util.Comparator;

/**
 * 根据会议的开始时间升序排序
 * Created by lei.han on 2016/8/4.
 */
public class StartTimeComparator implements Comparator<Conference> {
    @Override
    public int compare(Conference o1, Conference o2) {
        if (o1.getStartTime() > o2.getStartTime()) {
            return 1;
        } else if (o1.getStartTime() == o2.getStartTime()) {
            return 0;
        }
        return -1;
    }
}
