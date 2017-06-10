package com.roombook.vo;

/**
 * Created by deru on 2017/5/13.
 */
public class BookUrlComponent {

    public String classId;
    public String devId;
    public String kindId;
    public String labId;
    public String start;
    public String end;
    public String startTime;
    public String endTime;
    public String studentId;
    public String studentPwd;
    public String userId;
    public Long bookInfoId;

    public BookUrlComponent(String classId, String devId, String kindId, String labId, String start, String end, String startTime, String endTime, String studentId, String studentPwd, String userId, Long bookInfoId) {
        this.classId = classId;
        this.devId = devId;
        this.kindId = kindId;
        this.labId = labId;
        this.start = start;
        this.end = end;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.studentPwd = studentPwd;
        this.userId = userId;
        this.bookInfoId = bookInfoId;
    }
}
