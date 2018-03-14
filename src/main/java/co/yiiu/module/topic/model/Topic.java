package co.yiiu.module.topic.model;

import co.yiiu.core.util.Constants;
import co.yiiu.module.node.model.Node;
import co.yiiu.module.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Indexed
@Analyzer(impl = SmartChineseAnalyzer.class)
@Entity
@Table(name = "yiiu_topic")
@Getter
@Setter
public class Topic implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  //节点
  @ManyToOne
  @JoinColumn(nullable = false, name = "node_id")
  private Node node;

  //标题
  @Field(store = Store.YES)
  @Column(unique = true, nullable = false)
  private String title;

  // 转载文章的url
  private String url;

  @Field(store = Store.YES)
  //内容
  @Column(columnDefinition = "text")
  private String content;

  //发布时间
  @Field
  @Column(nullable = false)
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private Date inTime;

  //修改时间
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  @Column(name = "modify_time")
  private Date modifyTime;

  // last comment date
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  @Column(name = "last_comment_time")
  private Date lastCommentTime;

  //是否置顶
  private boolean top;

  //是否精华
  private boolean good;

  //浏览数
  @Column(nullable = false)
  private int view;

  //与用户的关联关系
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  @JsonIgnore
  private User user;

  //评论数
  @Column(name = "comment_count")
  private int commentCount;

  @Column(columnDefinition = "text")
  //点赞用户id，逗号隔开(英文逗号)
  private String upIds;

  //问题是否被锁定
  @Column(name = "topic_lock")
  private boolean lock;

}
