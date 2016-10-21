package cn.tomoya.module.topic.elastic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Service
public class ElasticTopicService {

    @Autowired
    private ElasticTopicRepository elasticTopicRepository;

    /**
     * 检索一条数据
     * @param elasticTopic
     */
    public void save(ElasticTopic elasticTopic) {
        elasticTopicRepository.save(elasticTopic);
    }

    /**
     * 删除索引
     * @param elasticTopic
     */
    public void delete(ElasticTopic elasticTopic) {
        elasticTopicRepository.delete(elasticTopic);
    }

    /**
     * 根据关键字查询话题
     * @param p
     * @param size
     * @param keyword
     * @return
     */
    public Page<ElasticTopic> pageByKeyword(int p, int size, String keyword) {
        Pageable pageable = new PageRequest(p - 1, size);
        return elasticTopicRepository.findByTitleStartingWith(keyword, pageable);
    }

    /**
     * 根据话题id查询检索的记录
     * @param topicId
     * @return
     */
    public ElasticTopic findByTopicId(int topicId) {
        return elasticTopicRepository.findByTopicId(topicId);
    }
}
