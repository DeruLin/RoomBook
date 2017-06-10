package com.roombook.vo;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by deru on 2017/5/13.
 */
public class BookInfoUI {


    public long id;
    public String roomName;
    public Date date;
    public String startTime;
    public String endTime;
    public String status;
    public int realStatus;

    public BookInfoUI(long id, String roomName, Date date, String startTime, String endTime, String status, int realStatus) {
        this.id = id;
        this.roomName = roomName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.realStatus = realStatus;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(int realStatus) {
        this.realStatus = realStatus;
    }
}
