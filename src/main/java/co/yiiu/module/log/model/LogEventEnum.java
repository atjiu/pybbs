package co.yiiu.module.log.model;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
public enum LogEventEnum {

  CREATE_TOPIC("发布主题", "createTopic"),
  COMMENT_TOPIC("评论主题", "commentTopic"),
  REPLY_COMMENT("回复评论", "replyComment"),
  EDIT_TOPIC("编辑话题", "editTopic"),
  COLLECT_TOPIC("收藏话题", "collectTopic"),
  DELETE_COLLECT_TOPIC("删除收藏的话题", "deleteCollectTopic"),
  DELETE_TOPIC("删除话题", "deleteTopic"),
  EDIT_COMMENT("编辑评论", "editComment"),
  DELETE_COMMENT("删除评论", "deleteComment"),
  UP_TOPIC("点赞话题", "upTopic"),
  DOWN_TOPIC("点踩话题", "downTopic"),
  UP_COMMENT("点赞评论", "upComment"),
  DOWN_COMMENT("点踩评论", "downComment");

  private String event;
  private String name;

  LogEventEnum(String event, String name) {
    this.event = event;
    this.name = name;
  }

  LogEventEnum(String event) {
    this.event = event;
  }

  public String getEvent() {
    return event;
  }

  public String getName() {
    return name;
  }
}
