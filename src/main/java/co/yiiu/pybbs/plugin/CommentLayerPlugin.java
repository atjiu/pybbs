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

    private List<CommentsByTopic> sortByLayerV2(List<CommentsByTopic> comments) {
        // 获取第一级评论
        List<CommentsByTopic> firstComments = comments.stream().filter(
                item -> item.getCommentId() == null).collect(Collectors.toList());
        // 获取非一级评论
        List<CommentsByTopic> otherComment = comments.stream().filter(
                item -> item.getCommentId() != null).collect(Collectors.toList());
        // 递归获取评论的回复 设置楼层（父楼层+1）
        Map<Integer, List<CommentsByTopic>> otherCommentMap = otherComment.stream().collect(Collectors.groupingBy(item -> item.getCommentId()));
        List<CommentsByTopic> resultList = new ArrayList<>();
        for (CommentsByTopic firstComment : firstComments) {
            resultList.add(firstComment);
            List<CommentsByTopic> subComment = otherCommentMap.get(firstComment.getId());
            if (CollectionUtils.isEmpty(subComment)) {
                continue;
            }
            resultList = setCommentLayer(subComment, firstComment.getLayer(), otherCommentMap,resultList);

        }
        return resultList;
    }

    private List<CommentsByTopic> setCommentLayer(List<CommentsByTopic> subComments,Integer layer,
                                                  Map<Integer,List<CommentsByTopic>> otherCommentMap,
                                                  List<CommentsByTopic> resultList) {
        for (CommentsByTopic topic : subComments) {
            topic.setLayer(layer + 1);
            resultList.add(topic);
            List<CommentsByTopic> commentsByTopics = otherCommentMap.get(topic.getId());
            if (CollectionUtils.isEmpty(commentsByTopics)) {
                continue;
            }
            setCommentLayer(commentsByTopics,topic.getLayer(),otherCommentMap,resultList);
        }
        return resultList;
    }
}
