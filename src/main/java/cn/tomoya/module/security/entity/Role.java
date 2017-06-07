package cn.tomoya.module.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cn.tomoya.config.base.BaseEntity;
import cn.tomoya.module.user.entity.User;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_role")
public class Role extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private int id;

  // 权限标识
  private String name;

  // 权限描述
  private String description;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new HashSet<>();

  // 角色与权限的关联关系
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "pybbs_role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
      @JoinColumn(name = "permission_id") })
  private Set<Permission> permissions = new HashSet<>();

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

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }
}
