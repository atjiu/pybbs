package co.yiiu.config.data;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class DataRole {

    private String name;
    private String description;
    private List<String> permissions;

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

    public List<String> getPermissions() {
      return permissions;
    }

    public void setPermissions(List<String> permissions) {
      this.permissions = permissions;
    }
  }