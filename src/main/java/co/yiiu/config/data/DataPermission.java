package co.yiiu.config.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Getter
@Setter
public class DataPermission {

  private String name;
  private String description;
  private String url;
  private List<DataChildPermission> childs;

}
