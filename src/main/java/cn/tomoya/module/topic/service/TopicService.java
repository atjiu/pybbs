package cn.tomoya.module.topic.service;

import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.dao.TopicDao;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicDao topicDao;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    public void save(Topic topic) {
        topicDao.save(topic);
    }

    public Topic findById(int id) {
        return topicDao.findOne(id);
    }

    /**
     * delete topic
     *
     * @param id
     */
    public void deleteById(int id) {
        //delete all comment of the topic
        replyService.deleteByTopic(id);
        //delete topic
        topicDao.delete(id);
    }

    /**
     * delete all topics of the user
     *
     * @param user
     */
    public void deleteByUser(User user) {
        topicDao.deleteByUser(user);
    }

    /**
     * paging query topics
     *
     * @param p
     * @param size
     * @return
     */
    public Page<Topic> page(int p, int size, String tab) {
        String tab_all = localeMessageSourceUtil.getMessage("site.tab.all");
        String tab_good = localeMessageSourceUtil.getMessage("site.tab.good");
        String tab_unanswered = localeMessageSourceUtil.getMessage("site.tab.unanswered");
        Sort sort = new Sort(
                new Sort.Order(Sort.Direction.DESC, "top"),
                new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        if (tab.equals(tab_all)) {
            return topicDao.findAll(pageable);
        } else if (tab.equals(tab_good)) {
            return topicDao.findByGood(true, pageable);
        } else if (tab.equals(tab_unanswered)) {
            return topicDao.findByReplyCount(0, pageable);
        } else {
            return topicDao.findByTab(tab, pageable);
        }
    }

    /**
     * search by keyword
     * @param p
     * @param size
     * @param q
     * @return
     */
    public Page<Topic> search(int p, int size, String q) {
        if(StringUtils.isEmpty(q)) return null;
        Sort sort = new Sort(
                new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        return topicDao.findByTitleContainingOrContentContaining(q, q, pageable);
    }

    /**
     * topic comment number - 1
     *
     * @param topicId
     */
    public void reduceOneReplyCount(int topicId) {
        Topic topic = findById(topicId);
        if (topic != null) {
            topic.setReplyCount(topic.getReplyCount() - 1);
            save(topic);
        }
    }

    /**
     * query user topics
     *
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
