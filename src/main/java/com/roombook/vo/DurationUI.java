package com.roombook.vo;

/**
 * Created by deru on 2017/5/13.
 */
public class DurationUI {

    public String startTime;

    public String endTime;

    public DurationUI(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
