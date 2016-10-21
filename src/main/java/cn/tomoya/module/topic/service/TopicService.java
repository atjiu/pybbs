package cn.tomoya.module.topic.service;

import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.dao.TopicDao;
import cn.tomoya.module.topic.elastic.ElasticTopic;
import cn.tomoya.module.topic.elastic.ElasticTopicService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.Constants;
import com.github.javautils.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Service
@Transactional
public class TopicService {

    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private TopicDao topicDao;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private ElasticTopicService elasticTopicService;

    public void save(Topic topic) {
        topicDao.save(topic);
        if(siteConfig.isElastic()) {
            new Thread(() -> {
                ElasticTopic elasticTopic = new ElasticTopic();
                elasticTopic.setTopicId(topic.getId());
                elasticTopic.setTitle(topic.getTitle());
                elasticTopic.setContent(topic.getContent());
                elasticTopicService.save(elasticTopic);
            }).start();
        }
    }

    public Topic findById(int id) {
        return topicDao.findOne(id);
    }

    /**
     * 删除话题
     *
     * @param id
     */
    public void deleteById(int id) {
        if(siteConfig.isElastic()) {
            new Thread(() -> {
                ElasticTopic elasticTopic = elasticTopicService.findByTopicId(id);
                elasticTopicService.delete(elasticTopic);
            }).start();
        }
        //删除话题下面的回复
        replyService.deleteByTopic(id);
        //删除话题
        topicDao.delete(id);
    }

    /**
     * 删除用户发的所有话题
     * @param user
     */
    public void deleteByUser(User user) {
        topicDao.deleteByUser(user);
    }

    /**
     * 分页查询话题列表
     *
     * @param p
     * @param size
     * @return
     */
    public Page<Topic> page(int p, int size, String tab) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        if (tab.equals("全部")) {
            return topicDao.findAll(pageable);
        } else if(tab.equals("精华")) {
            return topicDao.findByGood(true, pageable);
        } else if(tab.equals("等待回复")) {
            return topicDao.findByReplyCount(0, pageable);
        } else {
            return topicDao.findByTab(tab, pageable);
        }
    }

    /**
     * 点赞
     *
     * @param userId
     * @param topicId
     */
    public void addOneUp(int userId, int topicId) {
        Topic topic = findById(topicId);
        if (topic != null) {
            topic.setUp(topic.getUp() + 1);
            topic.setUpIds(topic.getUpIds() + userId + Constants.COMMA);
            save(topic);
        }
    }

    /**
     * 取消点赞
     *
     * @param userId
     * @param topicId
     */
    public void reduceOneUp(int userId, int topicId) {
        Topic topic = findById(topicId);
        if (topic != null) {
            String upIds = topic.getUpIds();
            upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
            topic.setUpIds(upIds);
            topic.setUp(topic.getUp() - 1);
            save(topic);
        }
    }

    /**
     * 查询用户的话题
     * @param p
     * @param size
     * @param user
     * @return
     */
    public Page<Topic> findByUser(int p, int size, User user) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        return topicDao.findByUser(user, pageable);
    }

}
