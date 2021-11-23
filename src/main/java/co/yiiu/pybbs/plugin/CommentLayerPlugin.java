package co.yiiu.pybbs.plugin;

import co.yiiu.pybbs.model.vo.CommentsByTopic;
import co.yiiu.pybbs.service.impl.SystemConfigService;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@Aspect
public class CommentLayerPlugin {

    @Resource
    private SystemConfigService systemConfigService;

    @Around("co.yiiu.pybbs.hook.CommentServiceHook.selectByTopicId()")
    public Object selectByTopicId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        List<CommentsByTopic> newComments = (List<CommentsByTopic>) proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        if (systemConfigService.selectAllConfig().get("comment_layer").equals("1")) {
            // 盖楼显示评论
            return this.sortByLayerV2(newComments);
        }
        return newComments;
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
                    int count = 0;
                    if (commentIdIndex != -1) {
                        for (CommentsByTopic newComment : newComments) {
                            if (newComments.get(commentIdIndex).getId().equals(newComment.getCommentId())) count++;
                        }
                    }
                    newComments.add(commentIdIndex == -1 ? idIndex + 1 : commentIdIndex + 1 + count, comment);
                }
            }
        }
        return newComments;
    }

    private List<CommentsByTopic> sortByLayerV2(List<CommentsByTopic> comments){
        List<CommentsByTopic> resultList = new ArrayList<>(comments.size());
        int size = comments.size();
        for(int i = 0; i< size; i++) {
            CommentsByTopic commentsByTopic = comments.get(i);
            if (commentsByTopic.getCommentId() != null) {
                continue;
            }
            resultList.add(commentsByTopic);
            CommentsByTopic temp = commentsByTopic;
            for(int j = 0; j < size; j++) {
                CommentsByTopic nestedComment = comments.get(j);
                if (nestedComment.getCommentId() == null) {
                    continue;
                }
                if (!nestedComment.getCommentId().equals(commentsByTopic.getId()) && !nestedComment.getCommentId().equals(temp.getId())) {
                    continue;
                }
                // 第一层
                if (nestedComment.getCommentId().equals(commentsByTopic.getId())) {
                    nestedComment.setLayer(1);
                    resultList.add(nestedComment);
                    temp = nestedComment;
                    continue;

                }
                // 非一层
                if (nestedComment.getCommentId().equals(temp.getId())) {
                    nestedComment.setLayer(temp.getLayer() + 1);
                    resultList.add(nestedComment);
                    temp = nestedComment;
                }
            }
        }
        return resultList;
    }
}
