package cn.tomoya.module.section.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.tomoya.config.base.BaseEntity;

/** 
 * Created by tomoya. 
 * Copyright (c) 2016, All Rights Reserved. 
 * http://tomoya.cn 
 */
@Entity
@Table(name = "pybbs_section")
public class Section extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

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