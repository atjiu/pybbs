package co.yiiu.module.user.model;

import co.yiiu.core.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Entity
@Table(name = "yiiu_user")
@Getter
@Setter
public class User implements Serializable {

  /**
   * @Id 映射主键属性
   * @GeneratedValue —— 注解声明了主键的生成策略。该注解有如下属性
   * strategy 指定生成的策略,默认是GenerationType.AUTO
   * GenerationType.AUTO 主键由程序控制
   * GenerationType.TABLE 使用一个特定的数据库表格来保存主键
   * GenerationType.IDENTITY 主键由数据库自动生成,主要是自动增长类型
   * GenerationType.SEQUENCE 根据底层数据库的序列来生成主键，条件是数据库支持序列
   * generator 指定生成主键使用的生成器
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // 用户名
  @Column(unique = true, nullable = false)
  private String username;

  // 密码
  @Column(nullable = false)
  @JsonIgnore
  private String password;

  // 头像
  @Column(nullable = false)
  private String avatar;

  // 用户邮箱
  @Column(unique = true)
  private String email;

  // 用户手机号
  @Column(unique = true)
  private String mobile;

  // 个人签名
  @Column(length = 64)
  private String bio;

  // 个人主页
  private String url;

  // 注册时间
  @Column(nullable = false)
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private Date inTime;

  // 用户是否被禁用
  private Boolean block;

  // 用户令牌
  private String token;

  // 用户声望
  @Column(columnDefinition = "INT DEFAULT 0")
  private Integer reputation;

  // 评论话题发邮件
  private Boolean commentEmail;

  // 回复评论发邮件
  private Boolean replyEmail;

}
