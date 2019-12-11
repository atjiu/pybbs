package co.yiiu.pybbs.service.impl;

import co.yiiu.pybbs.mapper.TagMapper;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.TopicTag;
import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.service.ITagService;
import co.yiiu.pybbs.service.ITopicTagService;
import co.yiiu.pybbs.util.MyPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
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
public class TagService implements ITagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ITopicTagService topicTagService;
    @Autowired
    private ISystemConfigService systemConfigService;

    @Override
    public void selectTagsByTopicId(MyPage<Map<String, Object>> page) {
        page.getRecords().forEach(map -> {
            List<TopicTag> topicTags = topicTagService.selectByTopicId((Integer) map.get("id"));
            if (!topicTags.isEmpty()) {
                List<Integer> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
                List<Tag> tags = this.selectByIds(tagIds);
                map.put("tags", tags);
            }
        });
    }

    @Override
    public Tag selectById(Integer id) {
        return tagMapper.selectById(id);
    }

    @Override
    public Tag selectByName(String name) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Tag::getName, name);
        return tagMapper.selectOne(wrapper);
    }

    @Override
    public List<Tag> selectByIds(List<Integer> ids) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(Tag::getId, ids);
        return tagMapper.selectList(wrapper);
    }

    // 根据话题查询关联的所有标签
    @Override
    public List<Tag> selectByTopicId(Integer topicId) {
        List<TopicTag> topicTags = topicTagService.selectByTopicId(topicId);
        if (!topicTags.isEmpty()) {
            List<Integer> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
            QueryWrapper<Tag> wrapper = new QueryWrapper<>();
            wrapper.lambda().in(Tag::getId, tagIds);
            return tagMapper.selectList(wrapper);
        }
        return Lists.newArrayList();
    }

    // 将创建话题时填的tag处理并保存
    @Override
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
                tag.setInTime(new Date());
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
    @Override
    public void reduceTopicCount(Integer id) {
        List<Tag> tags = this.selectByTopicId(id);
        tags.forEach(tag -> {
            tag.setTopicCount(tag.getTopicCount() - 1);
            tagMapper.updateById(tag);
        });
    }

    // 查询标签关联的话题
    @Override
    public MyPage<Map<String, Object>> selectTopicByTagId(Integer tagId, Integer pageNo) {
        MyPage<Map<String, Object>> iPage = new MyPage<>(pageNo, Integer.parseInt(systemConfigService.selectAllConfig()
                .get("page_size").toString()));
        return tagMapper.selectTopicByTagId(iPage, tagId);
    }

    // 查询标签列表
    @Override
    public IPage<Tag> selectAll(Integer pageNo, Integer pageSize, String name) {
        IPage<Tag> iPage = new MyPage<>(pageNo, pageSize == null ? Integer.parseInt(systemConfigService.selectAllConfig()
                .get("page_size").toString()) : pageSize);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        // 当传进来的name不为null的时候，就根据name查询
        if (!StringUtils.isEmpty(name)) {
            wrapper.lambda().eq(Tag::getName, name);
        }
        wrapper.orderByDesc("topic_count");
        return tagMapper.selectPage(iPage, wrapper);
    }

    @Override
    public void update(Tag tag) {
        tagMapper.updateById(tag);
    }

    // 如果 topic_tag 表里还有关联的数据，这里删除会报错
    @Override
    public void delete(Integer id) {
        tagMapper.deleteById(id);
    }

    // ---------------------------- admin ----------------------------


    //同步标签的话题数
    @Override
    public void async() {
        List<Tag> tags = tagMapper.selectList(null);
        tags.forEach(tag -> {
            List<TopicTag> topicTags = topicTagService.selectByTagId(tag.getId());
            tag.setTopicCount(topicTags.size());
            this.update(tag);
        });
    }

    // 查询今天新增的标签数
    @Override
    public int countToday() {
        return tagMapper.countToday();
    }
}
