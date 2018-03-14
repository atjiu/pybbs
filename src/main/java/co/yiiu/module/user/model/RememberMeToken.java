package co.yiiu.module.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Beldon
 * Copyright (c) 2017/10/21, All Rights Reserved.
 * https://beldon.me/
 */
@Entity
@Table(name = "yiiu_remember_me_token")
@Getter
@Setter
public class RememberMeToken implements Serializable {
  @Id
  @GeneratedValue
  private int id;
  private String username;
  private String series;
  private String tokenValue;
  private Date date;

}
