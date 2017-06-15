package cn.tomoya.module.section.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_section")
public class Section implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  @Column(unique = true)
  private String name;

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
}