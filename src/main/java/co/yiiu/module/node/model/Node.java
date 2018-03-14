package co.yiiu.module.node.model;

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
@Table(name = "yiiu_node")
@Getter
@Setter
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

}