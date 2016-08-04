package com.leohan.hourview;

/**
 * Created by lei.han on 2016/8/3.
 */
public class Conference {
    private int id;
    private String desc;
    private long startTime, endTime;
    private long localUpdateTime;//该数据在本地数据库中的更新时间

    public Conference(int id, String desc, long startTime, long endTime, long localUpdateTime) {
        this.id = id;
        this.desc = desc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.localUpdateTime = localUpdateTime;
    }

    public Conference(String desc, long startTime, long endTime) {
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getLocalUpdateTime() {
        return localUpdateTime;
    }

    public void setLocalUpdateTime(long localUpdateTime) {
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
