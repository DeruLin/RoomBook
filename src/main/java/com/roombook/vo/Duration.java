package com.roombook.vo;

import java.sql.Time;

/**
 * Created by deru on 2017/5/12.
 */
public class Duration {

    public Time startTime;

    public Time endTime;

    public Duration(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
