package co.yiiu.pybbs.service;

import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.util.MyPage;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface ITagService {
    void selectTagsByTopicId(MyPage<Map<String, Object>> page);

    Tag selectById(Integer id);

    Tag selectByName(String name);

    List<Tag> selectByIds(List<Integer> ids);

    // 根据话题查询关联的所有标签
    List<Tag> selectByTopicId(Integer topicId);

    // 将创建话题时填的tag处理并保存
    List<Tag> insertTag(String newTags);

    // 将标签的话题数都-1
    void reduceTopicCount(Integer id);

    // 查询标签关联的话题
    MyPage<Map<String, Object>> selectTopicByTagId(Integer tagId, Integer pageNo);

    // 查询标签列表
    IPage<Tag> selectAll(Integer pageNo, Integer pageSize, String name);

    void update(Tag tag);

    // 如果 topic_tag 表里还有关联的数据，这里删除会报错
    void delete(Integer id);

    //同步标签的话题数
    void async();

    // 查询今天新增的标签数
    int countToday();
}
