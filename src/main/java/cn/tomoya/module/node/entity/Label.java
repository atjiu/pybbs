package cn.tomoya.module.node.entity;

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

  @Column(unique = true)
  private String name;

  // node introduction
  private String intro;

  @Column(name = "topic_count")
  private int topicCount;

  @Column(name = "in_time")
  private Date inTime;

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

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
