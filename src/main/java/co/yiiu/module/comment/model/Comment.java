package co.yiiu.module.comment.model;

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
@Table(name = "yiiu_comment")
@Getter
@Setter
public class Comment implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  //评论的内容
  @Column(columnDefinition = "text", nullable = false)
  private String content;

  //评论时间
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private Date inTime;

  //点赞个数
  @Column(nullable = false)
  private Integer up;

  //踩的个数
  @Column(nullable = false)
  private Integer down;

  //与话题的关联关系
  private Integer topicId;

  //与用户的关联关系
  private Integer userId;

  //对评论点赞的用户id，逗号隔开(英文逗号)
  @Column(columnDefinition = "text")
  private String upIds;

  //对评论点踩的用户id，逗号隔开(英文逗号)
  @Column(columnDefinition = "text")
  private String downIds;

  // 被评论对象的ID
  private Integer commentId;

  // 评价的分支深度，不作持久化
  @Transient
  private Integer layer;

}
