package co.yiiu.module.topic.model;

import co.yiiu.core.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "yiiu_topic")
@Getter
@Setter
public class Topic implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  //标题
  @Column(unique = true, nullable = false)
  private String title;

  //内容
  @Column(columnDefinition = "text")
  private String content;

  //发布时间
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
  private Boolean top;

  //是否精华
  private Boolean good;

  //浏览数
  @Column(nullable = false)
  private Integer view;

  //与用户的关联关系
  private Integer userId;

  //评论数
  @Column(name = "comment_count")
  private Integer commentCount;

  private Integer up;

  private Integer down;

  @Column(columnDefinition = "text")
  //点赞用户id，逗号隔开(英文逗号)
  private String upIds;

  @Column(columnDefinition = "text")
  //点赞用户id，逗号隔开(英文逗号)
  private String downIds;

  // 话题权重，用于排序的，参考：https://meta.stackexchange.com/questions/11602/what-formula-should-be-used-to-determine-hot-questions
  // 话题里的weight计算方法参考stackoverflow的计算公式
  @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
  private Double weight;

  // 冗余字段
  private String tag;

}
