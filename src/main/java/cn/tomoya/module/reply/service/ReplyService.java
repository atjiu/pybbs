package cn.tomoya.module.reply.service;

import cn.tomoya.module.reply.dao.ReplyDao;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Service
@Transactional
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;
    @Autowired
    private TopicService topicService;

    public Reply findById(int id) {
        return replyDao.findOne(id);
    }

    public void save(Reply reply) {
        replyDao.save(reply);
    }

    public Map delete(int id, User user) {
        Map map = new HashMap();
        Reply reply = findById(id);
        if (reply != null && user.getId() == reply.getUser().getId()) {
            map.put("topicId", reply.getTopic().getId());
            topicService.reduceOneReplyCount(reply.getTopic().getId());
            replyDao.delete(id);
        }
        return map;
    }

    /**
     * delete all comment by the user
     *
     * @param user
     */
    public void deleteByUser(User user) {
        replyDao.deleteByUser(user);
    }

    /**
     * delete comment by the topic
     *
     * @param topicId
     */
    public void deleteByTopic(int topicId) {
        replyDao.deleteByTopicId(topicId);
    }

    /**
     * up vote
     *
     * @param userId
     * @param replyId
     */
    public Reply up(int userId, int replyId) {
        Reply reply = findById(replyId);
        if (reply != null) {
            String upIds = reply.getUpIds();
            if(upIds == null) upIds = Constants.COMMA;
            if(!upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
                reply.setUp(reply.getUp() + 1);
                reply.setUpIds(upIds + userId + Constants.COMMA);

                String downIds = reply.getDownIds();
                if(downIds == null) downIds = Constants.COMMA;
                if(downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
                    reply.setDown(reply.getDown() - 1);
                    downIds = downIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
                    reply.setDownIds(downIds);
                }
                int count = reply.getUp() - reply.getDown();
                reply.setUpDown(count > 0 ? count : 0);
                save(reply);
            }
        }
        return reply;
    }

    /**
     * cancel up vote
     *
     * @param userId
     * @param replyId
     */
    public Reply cancelUp(int userId, int replyId) {
        Reply reply = findById(replyId);
        if (reply != null) {
            String upIds = reply.getUpIds();
            if(upIds == null) upIds = Constants.COMMA;
            if(upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
                reply.setUp(reply.getUp() - 1);
                upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
                reply.setUpIds(upIds);

                int count = reply.getUp() - reply.getDown();
                reply.setUpDown(count > 0 ? count : 0);
                save(reply);
            }
        }
        return reply;
    }

    /**
     * down vote
     *
     * @param userId
     * @param replyId
     */
    public Reply down(int userId, int replyId) {
        Reply reply = findById(replyId);
        if (reply != null) {
            String downIds = reply.getDownIds();
            if(downIds == null) downIds = Constants.COMMA;
            if(!downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
                reply.setDown(reply.getDown() + 1);
                reply.setDownIds(downIds + userId + Constants.COMMA);

                String upIds = reply.getUpIds();
                if(upIds == null) upIds = Constants.COMMA;
                if(upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
                    reply.setUp(reply.getUp() - 1);
                    upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
                    reply.setUpIds(upIds);
                }
                int count = reply.getUp() - reply.getDown();
                reply.setUpDown(count > 0 ? count : 0);
                save(reply);
            }
        }
        return reply;
    }

    /**
     * cancel down vote
     *
     * @param userId
     * @param replyId
     */
    public Reply cancelDown(int userId, int replyId) {
        Reply reply = findById(replyId);
        if (reply != null) {
            String downIds = reply.getDownIds();
            if(downIds == null) downIds = Constants.COMMA;
            if(downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
                reply.setDown(reply.getDown() - 1);
                downIds = downIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
                reply.setDownIds(downIds);

                int count = reply.getUp() - reply.getDown();
                reply.setUpDown(count > 0 ? count : 0);
                save(reply);
            }
        }
        return reply;
    }

    /**
     * query comment list by the topic
     *
     * @param topic
     * @return
     */
    public List<Reply> findByTopic(Topic topic) {
        return replyDao.findByTopicOrderByUpDownDescDownAscInTimeAsc(topic);
    }

    /**
     * paging query comment list
     *
     * @param p
     * @param size
     * @return
     */
    public Page<Reply> page(int p, int size) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        return replyDao.findAll(pageable);
    }

    /**
     * query comment list by the user
     *
     * @param p
     * @param size
     * @param user
     * @return
     */
    public Page<Reply> findByUser(int p, int size, User user) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        return replyDao.findByUser(user, pageable);
    }
}
