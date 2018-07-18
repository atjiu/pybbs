package co.yiiu.module.log.model;

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
@Table(name = "yiiu_log")
@Getter
@Setter
public class Log implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // 事件名
  private String event;

  // 事件描述
  private String eventDescription;

  //与用户的关联关系
  private Integer userId;

  // 操作对象，枚举里定义
  private String target;

  // 与事件有关联的对象id
  private Integer targetId;

  // 操作对象之前内容
  @Column(name = "before_content", columnDefinition = "TEXT")
  private String before;

  // 操作对象之后内容
  @Column(name = "after_content", columnDefinition = "TEXT")
  private String after;

  private Date inTime;

}
