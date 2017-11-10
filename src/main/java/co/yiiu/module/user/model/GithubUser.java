package co.yiiu.module.user.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Entity
@Table(name = "yiiu_github_user")
public class GithubUser implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  /**
   * @OneToOne：一对一关联
   * mappedBy = "githubUser"：意思是说这里的一对一配置参考了githubUser
   * githubUser又是什么呢?githubUser是User类中的getGithubUser(),注意不是User类中的
   * githubUser属性,User类中的OneToOne配置就是在getGithubUser()方法上面配的.
   * 如果User类中的getGithubUser()方法改成getIdGithubUser(),其他不变的话,
   * 这里就要写成：mappedBy = "idGithubUser"
   *
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getGithubId() {
    return githubId;
  }

  public void setGithubId(String githubId) {
    this.githubId = githubId;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getAvatar_url() {
    return avatar_url;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getHtml_url() {
    return html_url;
  }

  public void setHtml_url(String html_url) {
    this.html_url = html_url;
  }

  public String getFollowers_url() {
    return followers_url;
  }

  public void setFollowers_url(String followers_url) {
    this.followers_url = followers_url;
  }

  public String getFollowing_url() {
    return following_url;
  }

  public void setFollowing_url(String following_url) {
    this.following_url = following_url;
  }

  public String getGists_url() {
    return gists_url;
  }

  public void setGists_url(String gists_url) {
    this.gists_url = gists_url;
  }

  public String getStarted_url() {
    return started_url;
  }

  public void setStarted_url(String started_url) {
    this.started_url = started_url;
  }

  public String getSubscriptions_url() {
    return subscriptions_url;
  }

  public void setSubscriptions_url(String subscriptions_url) {
    this.subscriptions_url = subscriptions_url;
  }

  public String getOrganizations_url() {
    return organizations_url;
  }

  public void setOrganizations_url(String organizations_url) {
    this.organizations_url = organizations_url;
  }

  public String getRepos_url() {
    return repos_url;
  }

  public void setRepos_url(String repos_url) {
    this.repos_url = repos_url;
  }

  public String getEvents_url() {
    return events_url;
  }

  public void setEvents_url(String events_url) {
    this.events_url = events_url;
  }

  public String getReceived_events_url() {
    return received_events_url;
  }

  public void setReceived_events_url(String received_events_url) {
    this.received_events_url = received_events_url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isSite_admin() {
    return site_admin;
  }

  public void setSite_admin(boolean site_admin) {
    this.site_admin = site_admin;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getBlog() {
    return blog;
  }

  public void setBlog(String blog) {
    this.blog = blog;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHireable() {
    return hireable;
  }

  public void setHireable(String hireable) {
    this.hireable = hireable;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public int getPublic_repos() {
    return public_repos;
  }

  public void setPublic_repos(int public_repos) {
    this.public_repos = public_repos;
  }

  public int getPublic_gists() {
    return public_gists;
  }

  public void setPublic_gists(int public_gists) {
    this.public_gists = public_gists;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  public int getFollowing() {
    return following;
  }

  public void setFollowing(int following) {
    this.following = following;
  }
}
