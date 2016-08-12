package com.leohan.customview.common;

/**
 * Created by lei.han on 2016/8/3.
 */
public class Conference {
    private int id;
    private String desc;
    private float startTime, endTime;
    private float localUpdateTime;//该数据在本地数据库中的更新时间

    public Conference(int id, String desc, float startTime, float endTime) {
        this.id = id;
        this.desc = desc;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public float getLocalUpdateTime() {
        return localUpdateTime;
    }

    public void setLocalUpdateTime(float localUpdateTime) {
        this.localUpdateTime = localUpdateTime;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "desc='" + desc + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
