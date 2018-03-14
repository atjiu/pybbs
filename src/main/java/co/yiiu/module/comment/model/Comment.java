package co.yiiu.module.comment.model;

import co.yiiu.core.util.Constants;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
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
  @GeneratedValue
  private int id;

  //评论的内容
  @Column(columnDefinition = "text", nullable = false)
  private String content;

  //评论时间
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
  private Date inTime;

  //点赞个数
  @Column(nullable = false)
  private int up;

  //踩的个数
  @Column(nullable = false)
  private int down;

  @Column(nullable = false, name = "up_down")
  private int upDown;

  //与话题的关联关系
  @ManyToOne
  @JoinColumn(nullable = false, name = "topic_id")
  private Topic topic;

  //与用户的关联关系
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  //对评论点赞的用户id，逗号隔开(英文逗号)
  @Column(columnDefinition = "text")
  private String upIds;

  //对评论点踩的用户id，逗号隔开(英文逗号)
  @Column(columnDefinition = "text")
  private String downIds;

}
