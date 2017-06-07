package cn.tomoya.module.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cn.tomoya.config.base.BaseEntity;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_permission")
public class Permission extends BaseEntity implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  // 权限名称
  private String name;

  // 权限描述
  private String description;

  // 授权链接
  private String url;

  // 父节点id
  private int pid;

  /**
   * 角色与权限的关联关系
   * mappedBy: 就是 Role.class 里的 Set<Permission> 的对象名
   */
  @ManyToMany(mappedBy = "permissions")
  private Set<Role> roles = new HashSet<>();

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
