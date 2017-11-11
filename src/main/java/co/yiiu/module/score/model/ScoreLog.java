package co.yiiu.module.score.model;

import co.yiiu.core.util.Constants;
import co.yiiu.module.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Table(name = "yiiu_score_log")
@Entity
public class ScoreLog  implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    /**
     * 事件名
     */
    private String event;

    /**
     * 事件描述
     */
    @Column(name = "event_description")
    private String eventDescription;

    /**
     * 变动积分
     */
    @Column(name = "change_score")
    private int changeScore;

    /**
     * 变动积分余额
     */
    private int score;

    //与用户的关联关系
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "in_time")
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date inTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public int getChangeScore() {
        return changeScore;
    }

    public void setChangeScore(int changeScore) {
        this.changeScore = changeScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
