package co.yiiu.pybbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class SystemConfig implements Serializable {

  @TableId(type = IdType.AUTO)
  private Integer id;

  // 配置键
  @TableField("`key`")
  private String key;
  // 配置值
  @TableField("`value`")
  private String value;
  // 配置描述
  private String description;
  private Integer pid;

  // 配置类型，常见的有 select, input[type=text,url,number,radio,password,email]
  @TableField("`type`")
  private String type;

  // 特殊类型里的值，比如 radio，select 的option
  @TableField("`option`")
  private String option;

  // 修改后是否需要重启
  @TableField("`reboot`")
  private String reboot;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPid() {
    return pid;
  }

  public void setPid(Integer pid) {
    this.pid = pid;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public String getReboot() {
    return reboot;
  }

  public void setReboot(String reboot) {
    this.reboot = reboot;
  }
}
