package co.yiiu.module.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Entity
@Table(name = "yiiu_system")
@Getter
@Setter
public class System implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;

  //系统变量名，不能重复
  @Column(unique = true)
  private String name;

  //变量值，不能为空
  @Column(nullable = false)
  private String value;

}
