package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.TopicTagMapper;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.TopicTag;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class TopicTagService {

  @Autowired
  private TopicTagMapper topicTagMapper;

  public List<TopicTag> selectByTopicId(Integer topicId) {
    QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(TopicTag::getTopicId, topicId);
    return topicTagMapper.selectList(wrapper);
  }

  public List<TopicTag> selectByTagId(Integer tagId) {
    QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(TopicTag::getTagId, tagId);
    return topicTagMapper.selectList(wrapper);
  }

  public void insertTopicTag(Integer topicId, List<Tag> tagList) {
    // 先删除topicId对应的所有记录
    this.deleteByTopicId(topicId);
    // 循环保存对应关联
    tagList.forEach(tag -> {
      TopicTag topicTag = new TopicTag();
      topicTag.setTopicId(topicId);
      topicTag.setTagId(tag.getId());
      topicTagMapper.insert(topicTag);
    });
  }

  // 删除话题所有关联的标签记录
  public void deleteByTopicId(Integer id) {
    QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(TopicTag::getTopicId, id);
    topicTagMapper.delete(wrapper);
  }
}
