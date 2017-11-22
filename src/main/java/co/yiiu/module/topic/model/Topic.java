package co.yiiu.module.topic.model;

import co.yiiu.core.util.Constants;
import co.yiiu.module.node.model.Node;
import co.yiiu.module.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Indexed
@Analyzer(impl = SmartChineseAnalyzer.class)
@Entity
@Table(name = "yiiu_topic")
public class Topic implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  //节点
  @ManyToOne
  @JoinColumn(nullable = false, name = "node_id")
  private Node node;

  //标题
  @Field(store = Store.YES)
  @Column(unique = true, nullable = false)
  private String title;

  // 转载文章的url
  private String url;

  @Field(store = Store.YES)
  //内容
  @Column(columnDefinition = "text")
  private String content;

  //发布时间
  @Field
  @Column(nullable = false)
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private Date inTime;

  //修改时间
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  @Column(name = "modify_time")
  private Date modifyTime;

  // last reply date
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  @Column(name = "last_reply_time")
  private Date lastReplyTime;

  //是否置顶
  private boolean top;

  //是否精华
  private boolean good;

  //浏览数
  @Column(nullable = false)
  private int view;

  //与用户的关联关系
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  @JsonIgnore
  private User user;

  //回复数
  @Column(name = "reply_count")
  private int replyCount;

  @Column(columnDefinition = "text")
  //点赞用户id，逗号隔开(英文逗号)
  private String upIds;

  //问题是否被锁定
  @Column(name = "topic_lock")
  private boolean lock;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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

  public Date getLastReplyTime() {
    return lastReplyTime;
  }

  public void setLastReplyTime(Date lastReplyTime) {
    this.lastReplyTime = lastReplyTime;
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

  public boolean isLock() {
    return lock;
  }

  public void setLock(boolean lock) {
    this.lock = lock;
  }
}
