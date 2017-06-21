package cn.tomoya.module.label.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya on 2017/6/16.
 */
@Entity
@Table(name = "pybbs_label")
public class Label implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  // label name
  @Column(nullable = false, unique = true)
  private String name;

  // label introduction
  private String intro;

  // the count of use this label's topic
  @Column(name = "topic_count")
  private int topicCount;

  @Column(name = "in_time", nullable = false)
  private Date inTime;

  @Column(name = "modify_time")
  private Date modifyTime;

  // label logo
  private String image;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTopicCount() {
    return topicCount;
  }

  public void setTopicCount(int topicCount) {
    this.topicCount = topicCount;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
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

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
