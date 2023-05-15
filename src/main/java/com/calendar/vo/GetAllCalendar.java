package com.calendar.vo;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
public class GetAllCalendar implements Serializable {

    @Id
    private Integer id;

    private Integer startTime;

    private Integer endTime;

    private String comments;

    private String nickName;

    private BigInteger status;

    private String photo;

    private Integer userId;

//    @Column(name = "nick_name")
//    private String nickName;

    public GetAllCalendar() {

    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public GetAllCalendar(Integer id, Integer startTime, Integer endTime, String comments, String nickName,String photo,Integer userId, BigInteger status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comments = comments;
        this.nickName = nickName;
        this.status = status;
        this.photo = photo;
        this.userId = userId;
    }

    // getters and setters


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

