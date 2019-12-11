package co.yiiu.pybbs.service;

import co.yiiu.pybbs.model.Collect;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.util.MyPage;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface ICollectService {
    // 查询话题被多少人收藏过
    List<Collect> selectByTopicId(Integer topicId);

    // 查询用户是否收藏过某个话题
    Collect selectByTopicIdAndUserId(Integer topicId, Integer userId);

    // 收藏话题
    Collect insert(Integer topicId, User user);

    // 删除（取消）收藏
    void delete(Integer topicId, Integer userId);

    // 根据话题id删除收藏记录
    void deleteByTopicId(Integer topicId);

    // 根据用户id删除收藏记录
    void deleteByUserId(Integer userId);

    // 查询用户收藏的话题数
    int countByUserId(Integer userId);

    // 查询用户收藏的话题
    MyPage<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize);
}
