package com.roombook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by deru on 2017/6/10.
 */
@Entity
@Table(name = "room_info", catalog = "roombook")
public class RoomInfo {

    public String id;
    public String classId;
    public String devId;
    public String kindId;
    public String labId;
    public String roomName;


    public RoomInfo() {
    }

    public RoomInfo(String id, String classId, String devId, String kindId, String labId, String roomName) {
        this.id = id;
        this.classId = classId;
        this.devId = devId;
        this.kindId = kindId;
        this.labId = labId;
        this.roomName = roomName;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "classId")
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Column(name = "devId")
    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    @Column(name = "kindId")
    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    @Column(name = "labId")
    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    @Column(name = "roomName")
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
