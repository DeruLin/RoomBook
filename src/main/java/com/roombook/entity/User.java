package com.roombook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "roombook")
public class User implements java.io.Serializable {

    // Fields

    private String studentId;
    private String openId;
    private String nickName;
    private String studentPwd;
    private String name;
    private String dept;
    private String phone;
    private String email;

    // Constructors

    /**
     * default constructor
     */
    public User() {
    }

    /**
     * full constructor
     */
    public User(String studentId, String nickName, String studentPwd,
                String name, String dept, String phone, String email) {
        this.studentId = studentId;
        this.nickName = nickName;
        this.studentPwd = studentPwd;
        this.name = name;
        this.dept = dept;
        this.phone = phone;
        this.email = email;
    }

    // Property accessors

    @Id
    @Column(name = "studentId")
    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Column(name = "openId", unique = true, nullable = false, length = 100)
    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String unionId) {
        this.openId = unionId;
    }

    @Column(name = "nickName")
    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "studentPwd")
    public String getStudentPwd() {
        return this.studentPwd;
    }

    public void setStudentPwd(String studentPwd) {
        this.studentPwd = studentPwd;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "dept")
    public String getDept() {
        return this.dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Column(name = "phone")
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}