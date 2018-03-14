package co.yiiu.config.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
@ConfigurationProperties
@Getter
@Setter
public class DataConfig {

  private DataUser _user;
  private List<DataRole> _roles;
  private List<DataPermission> _permissions;

}