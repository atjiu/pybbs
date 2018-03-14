package co.yiiu.module.collect.model;

import co.yiiu.core.util.Constants;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Entity
@Table(name = "yiiu_collect")
@Getter
@Setter
public class Collect implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  //与话题的关联关系
  @ManyToOne
  @JoinColumn(nullable = false, name = "topic_id")
  private Topic topic;

  //与用户的关联关系
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  @Column(name = "in_time")
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private Date inTime;

}

