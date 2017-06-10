package com.roombook.vo;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by deru on 2017/5/9.
 */
public class RoomInfoWithTime {

    public String id;
    public String classId;
    public String devId;
    public String kindId;
    public String labId;
    public String roomName;
    public List<DurationUI> remainTime;

    public RoomInfoWithTime(String id, String devId, String kindId, String classId, String labId, String roomName) {
        this.id = id;
        this.classId = classId;
        this.devId = devId;
        this.kindId = kindId;
        this.labId = labId;
        this.roomName = roomName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<DurationUI> getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(List<DurationUI> remainTime) {
        this.remainTime = remainTime;
    }
}
