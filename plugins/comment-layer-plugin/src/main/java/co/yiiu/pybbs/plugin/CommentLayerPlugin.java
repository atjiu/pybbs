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
    // ！！！想来想去还是要用到两层for循环，求大神优化这部分代码，越快越好！！！
    private List<CommentsByTopic> sortByLayer(List<CommentsByTopic> comments) {
        List<CommentsByTopic> newComments = new ArrayList<>();
        for (CommentsByTopic comment : comments) {
            if (comment.getCommentId() == null) {
                newComments.add(comment);
            } else {
                int idIndex = -1, commentIdIndex = -1;
                boolean idIndexFlag = false, commentIdIndexFlag = false;
                for (int i = newComments.size() - 1; i >= 0; i--) {
                    if (!idIndexFlag && comment.getCommentId().equals(newComments.get(i).getId())) {
                        idIndex = i;
                        idIndexFlag = true;
                    }
                    if (!commentIdIndexFlag && comment.getCommentId().equals(newComments.get(i).getCommentId())) {
                        commentIdIndex = i;
                        commentIdIndexFlag = true;
                    }
                }
                if (idIndex == -1) {
                    newComments.add(comment);
                } else {
                    int layer = newComments.get(idIndex).getLayer();
                    comment.setLayer(layer + 1);
                    newComments.add(commentIdIndex == -1 ? idIndex + 1 : commentIdIndex + 1, comment);
                }
            }
        }
        return newComments;
    }
}
