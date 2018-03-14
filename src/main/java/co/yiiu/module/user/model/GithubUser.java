package co.yiiu.module.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Entity
@Table(name = "yiiu_github_user")
@Getter
@Setter
public class GithubUser implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  /**
   * @OneToOne：一对一关联 mappedBy = "githubUser"：意思是说这里的一对一配置参考了githubUser
   * githubUser又是什么呢?githubUser是User类中的getGithubUser(),注意不是User类中的
   * githubUser属性,User类中的OneToOne配置就是在getGithubUser()方法上面配的.
   * 如果User类中的getGithubUser()方法改成getIdGithubUser(),其他不变的话,
   * 这里就要写成：mappedBy = "idGithubUser"
   * <p>
   * 我这没有配置在getter方法上，但配置是一样的
   * 参与：http://www.cnblogs.com/fancyzero/archive/2012/06/10/hibernate-one-to-one-annotation.html
   */
  @OneToOne(mappedBy = "githubUser")
  private User user;

  @Column(name = "github_id")
  private String githubId;

  private String login;

  private String avatar_url;

  private String url;

  private String html_url;

  private String followers_url;

  private String following_url;

  private String gists_url;

  private String started_url;

  private String subscriptions_url;

  private String organizations_url;

  private String repos_url;

  private String events_url;

  private String received_events_url;

  private String type;

  private boolean site_admin;

  private String name;

  private String company;

  private String blog;

  private String location;

  private String email;

  private String hireable;

  private String bio;

  private int public_repos;

  private int public_gists;

  private int followers;

  private int following;

}
