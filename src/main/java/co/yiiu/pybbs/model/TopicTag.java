package co.yiiu.pybbs.model;

import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class TopicTag implements Serializable {
  private static final long serialVersionUID = -5028599844989220715L;
  private Integer tagId;
  private Integer topicId;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Integer getTagId() {
    return tagId;
  }

  public void setTagId(Integer tagId) {
    this.tagId = tagId;
  }

  public Integer getTopicId() {
    return topicId;
  }

  public void setTopicId(Integer topicId) {
    this.topicId = topicId;
  }
}
