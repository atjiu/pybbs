package cn.tomoya.module.reply.entity;

import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_reply")
public class Reply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4727003589526236354L;

    @Id
    @GeneratedValue
    private int id;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date inTime;

    //number of up vote
    @Column(nullable = false)
    private int up;

    //number of down vote
    @Column(nullable = false)
    private int down;

    //the difference of up vote and down vote
    @Column(nullable = false, name = "up_down")
    private int upDown;

    @ManyToOne
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //up vote to the user's id, comma separated
    @Column(columnDefinition = "text")
    private String upIds;

    //down vote to the user's id, comma separated
    @Column(columnDefinition = "text")
    private String downIds;

    private String editor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getUpDown() {
        return upDown;
    }

    public void setUpDown(int upDown) {
        this.upDown = upDown;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUpIds() {
        return upIds;
    }

    public void setUpIds(String upIds) {
        this.upIds = upIds;
    }

    public String getDownIds() {
        return downIds;
    }

    public void setDownIds(String downIds) {
        this.downIds = downIds;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
