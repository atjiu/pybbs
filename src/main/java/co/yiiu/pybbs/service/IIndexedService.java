package co.yiiu.pybbs.service;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface IIndexedService {

    // 索引全部话题
    void indexAllTopic();

    // 索引话题
    void indexTopic(String id, String title, String content);

    // 删除话题索引
    void deleteTopicIndex(String id);

    // 删除所有话题索引
    void batchDeleteIndex();

}
