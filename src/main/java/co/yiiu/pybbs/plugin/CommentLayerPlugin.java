package co.yiiu.pybbs.plugin;

import co.yiiu.pybbs.model.vo.CommentsByTopic;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@Aspect
public class CommentLayerPlugin {

  @Around("co.yiiu.pybbs.hook.CommentServiceHook.selectByTopicId()")
  public Object selectByTopicId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    List<CommentsByTopic> newComments =
        (List<CommentsByTopic>) proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    // 盖楼显示评论
    return this.sortByLayer(newComments);
  }

  // 从列表里查找指定值的下标
  private int findLastIndex(List<CommentsByTopic> newComments, String key, Integer value) {
    int index = -1;
    for (int i = 0; i < newComments.size(); i++) {
      if (key.equals("commentId")) {
        if (value.equals(newComments.get(i).getCommentId())) {
          index = i;
        }
      } else if (key.equals("id")) {
        if (value.equals(newComments.get(i).getId())) {
          index = i;
        }
      }
    }
    return index;
  }

  // 盖楼排序
  private List<CommentsByTopic> sortByLayer(List<CommentsByTopic> comments) {
    List<CommentsByTopic> newComments = new ArrayList<>();
    comments.forEach(comment -> {
      if (comment.getCommentId() == null) {
        newComments.add(comment);
      } else {
        int index = this.findLastIndex(newComments, "commentId", comment.getCommentId());
        if (index == -1) {
          int upIndex = this.findLastIndex(newComments, "id", comment.getCommentId());
          if (upIndex == -1) {
            newComments.add(comment);
          } else {
            int layer = newComments.get(upIndex).getLayer() + 1;
            comment.setLayer(layer);
            newComments.add(upIndex + 1, comment);
          }
        } else {
          int layer = newComments.get(index).getLayer();
          comment.setLayer(layer);
          newComments.add(index + 1, comment);
        }
      }
    });
    return newComments;
  }
}
