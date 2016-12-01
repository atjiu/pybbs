package cn.tomoya.module.topic.entity;

import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_topic")
public class Topic extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1553496585975913731L;

    @Id
    @GeneratedValue
    private int id;

    //版块
    @Column(nullable = false)
    private String tab;

    //标题
    @Column(unique = true, nullable = false)
    private String title;

    //内容
    @Column(columnDefinition = "text")
    private String content;

    //发布时间
    @Column(nullable = false)
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date inTime;

    //修改时间
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date modifyTime;

    //是否置顶
    private boolean top;

    //是否精华
    private boolean good;

    //点赞个数
    @Column(nullable = false)
    private int up;

    //浏览数
    @Column(nullable = false)
    private int view;

    //与用户的关联关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //回复数
    @Column(name = "reply_count")
    private int replyCount;

    @Column(columnDefinition = "text")
    //点赞用户id，逗号隔开(英文逗号)
    private String upIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getUpIds() {
        return upIds;
    }

    public void setUpIds(String upIds) {
        this.upIds = upIds;
    }

}
