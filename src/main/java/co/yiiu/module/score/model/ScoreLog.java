package co.yiiu.module.score.model;

import co.yiiu.core.util.Constants;
import co.yiiu.module.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Entity
@Table(name = "yiiu_score_log")
@Getter
@Setter
public class ScoreLog implements Serializable {

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

}
