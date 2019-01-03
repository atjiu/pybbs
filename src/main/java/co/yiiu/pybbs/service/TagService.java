package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.TagMapper;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.TopicTag;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class TagService {

  @Autowired
  private TagMapper tagMapper;
  @Autowired
  private TopicTagService topicTagService;
  @Autowired
  private SystemConfigService systemConfigService;

  public Tag selectById(Integer id) {
    return tagMapper.selectById(id);
  }

  public Tag selectByName(String name) {
    QueryWrapper<Tag> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Tag::getName, name);
    return tagMapper.selectOne(wrapper);
  }

  public List<Tag> selectByIds(List<Integer> ids) {
    QueryWrapper<Tag> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .in(Tag::getId, ids);
    return tagMapper.selectList(wrapper);
  }

  // 根据话题查询关联的所有标签
  public List<Tag> selectByTopicId(Integer topicId) {
    List<TopicTag> topicTags = topicTagService.selectByTopicId(topicId);
    List<Integer> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
    QueryWrapper<Tag> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .in(Tag::getId, tagIds);
    return tagMapper.selectList(wrapper);
  }

  // 将创建话题时填的tag处理并保存
  public List<Tag> insertTag(String newTags) {
    // 使用工具将字符串按逗号分隔成数组
    String[] _tags = StringUtils.commaDelimitedListToStringArray(newTags);
    List<Tag> tagList = new ArrayList<>();
    for (String _tag : _tags) {
      Tag tag = this.selectByName(_tag);
      if (tag == null) {
        tag = new Tag();
        tag.setName(_tag);
        tag.setTopicCount(1);
        tagMapper.insert(tag);
      } else {
        tag.setTopicCount(tag.getTopicCount() + 1);
        tagMapper.updateById(tag);
      }
      tagList.add(tag);
    }
    return tagList;
  }

  // 将标签的话题数都-1
  public void reduceTopicCount(Integer id) {
    List<Tag> tags = this.selectByTopicId(id);
    tags.forEach(tag -> {
      tag.setTopicCount(tag.getTopicCount() - 1);
      tagMapper.updateById(tag);
    });
  }

  // 查询标签关联的话题
  public IPage<Map<String, Object>> selectTopicByTagId(Integer tagId, Integer pageNo) {
    IPage<Map<String, Object>> iPage = new Page<>(pageNo, Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString()));
    return tagMapper.selectTopicByTagId(iPage, tagId);
  }

  // 查询标签列表
  public IPage<Tag> selectAll(Integer pageNo, String name) {
    IPage<Tag> iPage = new Page<>(pageNo, Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString()));
    QueryWrapper<Tag> wrapper = new QueryWrapper<>();
    // 当传进来的name不为null的时候，就根据name查询
    if (!StringUtils.isEmpty(name)) {
      wrapper.lambda().eq(Tag::getName, name);
    }
    wrapper.orderByDesc("topic_count");
    return tagMapper.selectPage(iPage, wrapper);
  }

  public void update(Tag tag) {
    tagMapper.updateById(tag);
  }

  // 如果 topic_tag 表里还有关联的数据，这里删除会报错
  public void delete(Integer id) {
    tagMapper.deleteById(id);
  }

  // ---------------------------- admin ----------------------------


  //同步标签的话题数
  public void async() {
    List<Tag> tags = tagMapper.selectList(null);
    tags.forEach(tag -> {
      List<TopicTag> topicTags = topicTagService.selectByTagId(tag.getId());
      tag.setTopicCount(topicTags.size());
      this.update(tag);
    });
  }
}
