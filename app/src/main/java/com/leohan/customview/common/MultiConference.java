package com.leohan.customview.common;

/**
 * Created by lei.han on 2016/8/3.
 */
public class MultiConference {
    /**
     * 云会议
     **/
    public static final int CONF_CLOUD = 0;
    /**
     * 地点会议
     */
    public static final int CONF_PLACE = 1;
    /**
     * 非云会议非地点会议
     */
    public static final int CONF_NONE = 2;

    //会议唯一标识
    private int id;
    //会议描述
    private String desc;
    //会议开始时间、结束事件
    private float startTime, endTime;
    //会议类型（云会议0、地点会议1、非云会议非地点会议2）
    private int type = CONF_CLOUD;
    //会议是否正在编辑
    private boolean isEditing;

    public MultiConference(int id, String desc, float startTime, float endTime, int type) {
        this.id = id;
        this.desc = desc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    @Override
    public String toString() {
        return "MultiConference{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", type=" + type +
                ", isEditing=" + isEditing +
                '}';
    }
}
