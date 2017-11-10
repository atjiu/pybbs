package co.yiiu.config.data;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class DataPermission {

  private String name;
  private String description;
  private String url;
  private List<DataChildPermission> childs;

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

  public List<DataChildPermission> getChilds() {
    return childs;
  }

  public void setChilds(List<DataChildPermission> childs) {
    this.childs = childs;
  }
}
