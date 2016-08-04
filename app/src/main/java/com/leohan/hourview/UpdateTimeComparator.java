package com.leohan.hourview;

import java.util.Comparator;

/**
 * 根据该数据在本地数据库中的更新时间进行升序排序
 * Created by lei.han on 2016/8/4.
 */
public class UpdateTimeComparator implements Comparator<Conference> {
    @Override
    public int compare(Conference o1, Conference o2) {
        if (o1.getLocalUpdateTime() > o2.getLocalUpdateTime()) {
            return 1;
        } else if (o1.getLocalUpdateTime() == o2.getLocalUpdateTime()) {
            return 0;
        }
        return -1;
    }
}
