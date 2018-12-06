package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.CollectMapper;
import co.yiiu.pybbs.model.Collect;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.TopicTag;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CollectService {

  @Autowired
  private CollectMapper collectMapper;
  @Autowired
  private SystemConfigService systemConfigService;
  @Autowired
  private TagService tagService;
  @Autowired
  TopicTagService topicTagService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private NotificationService notificationService;

  // 查询话题被多少人收藏过
  public List<Collect> selectByTopicId(Integer topicId) {
    QueryWrapper<Collect> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Collect::getTopicId, topicId);
    return collectMapper.selectList(wrapper);
  }

  // 查询用户是否收藏过某个话题
  public Collect selectByTopicIdAndUserId(Integer topicId, Integer userId) {
    QueryWrapper<Collect> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Collect::getTopicId, topicId)
        .eq(Collect::getUserId, userId);
    List<Collect> collects = collectMapper.selectList(wrapper);
    if (collects.size() > 0) return collects.get(0);
    return null;
  }

  // 收藏话题
  public Collect insert(Integer topicId, Integer userId) {
    Collect collect = new Collect();
    collect.setTopicId(topicId);
    collect.setUserId(userId);
    collect.setInTime(new Date());
    collectMapper.insert(collect);

    // 通知
    Topic topic = topicService.selectById(topicId);
    // 收藏自己的话题不发通知
    if (!userId.equals(topic.getUserId())) {
      notificationService.insert(userId, topic.getUserId(), topicId, "COLLECT", null);
    }

    return collect;
  }

  // 删除（取消）收藏
  public void delete(Integer topicId, Integer userId) {
    QueryWrapper<Collect> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Collect::getTopicId, topicId)
        .eq(Collect::getUserId, userId);
    collectMapper.delete(wrapper);
  }

  // 根据话题id删除收藏记录
  public void deleteByTopicId(Integer topicId) {
    QueryWrapper<Collect> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Collect::getTopicId, topicId);
    collectMapper.delete(wrapper);
  }
  // 根据用户id删除收藏记录
  public void deleteByUserId(Integer userId) {
    QueryWrapper<Collect> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Collect::getUserId, userId);
    collectMapper.delete(wrapper);
  }

  // 查询用户收藏的话题数
  public int countByUserId(Integer userId) {
    QueryWrapper<Collect> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Collect::getUserId, userId);
    return collectMapper.selectCount(wrapper);
  }

  // 查询用户收藏的话题
  public IPage<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize) {
    IPage<Map<String, Object>> iPage = new Page<>(pageNo,
        pageSize == null ?
            Integer.parseInt(systemConfigService.selectAllConfig().get("pageSize").toString()) : pageSize
    );
    iPage = collectMapper.selectByUserId(iPage, userId);
    topicService.selectTags(iPage, topicTagService, tagService);
    return iPage;
  }
}
