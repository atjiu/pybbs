package co.yiiu.module.node.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Entity
@Table(name = "yiiu_node")
public class Node implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;

  //父节点id
  @Column(nullable = false, columnDefinition = "int default 0")
  private int pid;

  @Column
  private String name;

  @Column(unique = true)
  private String value;

  @Column(length = 1000)
  private String intro;

  @Column(name = "topic_count", nullable = false, columnDefinition = "int default 0")
  private Integer topicCount;

  @Column(name = "in_time")
  private Date inTime;

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public void setTopicCount(Integer topicCount) {
    this.topicCount = topicCount;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public int getTopicCount() {
    return topicCount;
  }

  public void setTopicCount(int topicCount) {
    this.topicCount = topicCount;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}