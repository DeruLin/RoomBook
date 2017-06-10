package com.roombook.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BookInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "book_info", catalog = "roombook")
public class BookInfo implements java.io.Serializable {

    // Fields

    private Long id;
    private String roomId;
    private String studentId;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int status;

    // Constructors

    /**
     * default constructor
     */
    public BookInfo() {
    }

    /**
     * full constructor
     */
    public BookInfo(String roomId, String studentId, Date date, Time startTime,
                    Time endTime,int status) {
        this.roomId = roomId;
        this.studentId = studentId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    // Property accessors
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "roomId")
    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Column(name = "studentId")
    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Column(name = "date")
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "startTime")
    public Time getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Column(name = "endTime")
    public Time getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Column(name = "status")
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}