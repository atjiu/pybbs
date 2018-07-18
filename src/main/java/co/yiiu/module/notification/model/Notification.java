package co.yiiu.module.notification.model;

import co.yiiu.core.util.Constants;
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
@Table(name = "yiiu_notification")
@Getter
@Setter
public class Notification implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  //通知是否已读
  @Column(name = "is_read")
  private Boolean isRead;

  //发起通知用户
  private Integer userId;

  //要通知用户
  private Integer targetUserId;

  @Column(name = "in_time")
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private Date inTime;

  //通知动作
  private String action;

  //关联的话题
  private Integer topicId;

  //通知内容（冗余字段）
  @Column(columnDefinition = "text")
  private String content;

}
