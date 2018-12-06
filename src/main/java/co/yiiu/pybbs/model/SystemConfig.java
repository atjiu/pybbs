package co.yiiu.pybbs.model;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class SystemConfig {

  // 配置键
  @TableField("`key`")
  private String key;
  // 配置值
  @TableField("`value`")
  private String value;
  // 配置描述
  private String description;

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
}
