package co.yiiu.pybbs.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 2018/9/4
 */
public class Topic implements Serializable {

  @Id
  private String id;
  private String title;
  // 转载文章的链接
  private String url;
  private String content;
  private String tab;
  private Date inTime;
  private Date modifyTime;
  private String userId;
  // 评论数
  private Integer commentCount;
  // 收藏数
  private Integer collectCount;
  // 浏览数
  private Integer view;
  // 置顶
  private Boolean top;
  // 加精
  private Boolean good;

  @Transient
  private User user;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getCollectCount() {
    return collectCount;
  }

  public void setCollectCount(Integer collectCount) {
    this.collectCount = collectCount;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTab() {
    return tab;
  }

  public void setTab(String tab) {
    this.tab = tab;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(Integer commentCount) {
    this.commentCount = commentCount;
  }

  public Integer getView() {
    return view;
  }

  public void setView(Integer view) {
    this.view = view;
  }

  public Boolean getTop() {
    return top;
  }

  public void setTop(Boolean top) {
    this.top = top;
  }

  public Boolean getGood() {
    return good;
  }

  public void setGood(Boolean good) {
    this.good = good;
  }
}
