package co.yiiu.module.user.model;

import co.yiiu.core.util.Constants;
import co.yiiu.module.security.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Entity
@Table(name = "yiiu_user")
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
  @GeneratedValue
  private int id;

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
  private boolean block;

  // 用户令牌
  private String token;

  // 用户积分
  private int score;

  // 尝试登录次数
  private int attempts;

  // 尝试登录时间
  @Column(name = "attempts_time")
  private Date attemptsTime;

  // user space size
  @Column(name = "space_size", nullable = false)
  private long spaceSize;

  // 用户与角色的关联关系
  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(
      name = "yiiu_user_role",
      joinColumns = {
          @JoinColumn(name = "user_id")
      },
      inverseJoinColumns = {
          @JoinColumn(name = "role_id")
      }
  )
  @JsonIgnore
  private Set<Role> roles = new HashSet<>();

  /**
   * @OneToOne：一对一关联
   * cascade：级联,它可以有有五个值可选,分别是：
   * CascadeType.PERSIST：级联新建
   * CascadeType.REMOVE : 级联删除
   * CascadeType.REFRESH：级联刷新
   * CascadeType.MERGE  ： 级联更新
   * CascadeType.ALL    ： 以上全部四项
   * @JoinColumn:主表外键字段
   * github_user_id：User所映射的表中的一个字段
   */
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "github_user_id")
  private GithubUser githubUser;

  public GithubUser getGithubUser() {
    return githubUser;
  }

  public void setGithubUser(GithubUser githubUser) {
    this.githubUser = githubUser;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public boolean isBlock() {
    return block;
  }

  public void setBlock(boolean block) {
    this.block = block;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getAttempts() {
    return attempts;
  }

  public void setAttempts(int attempts) {
    this.attempts = attempts;
  }

  public Date getAttemptsTime() {
    return attemptsTime;
  }

  public void setAttemptsTime(Date attemptsTime) {
    this.attemptsTime = attemptsTime;
  }

  public long getSpaceSize() {
    return spaceSize;
  }

  public void setSpaceSize(long spaceSize) {
    this.spaceSize = spaceSize;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
