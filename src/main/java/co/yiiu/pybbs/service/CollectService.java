package co.yiiu.pybbs.service;

import co.yiiu.pybbs.config.service.EmailService;
import co.yiiu.pybbs.mapper.CollectMapper;
import co.yiiu.pybbs.model.Collect;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.util.Constants;
import co.yiiu.pybbs.util.MyPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
  @Autowired
  private EmailService emailService;
  @Autowired
  private UserService userService;

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
  public Collect insert(Integer topicId, User user) {
    Collect collect = new Collect();
    collect.setTopicId(topicId);
    collect.setUserId(user.getId());
    collect.setInTime(new Date());
    collectMapper.insert(collect);

    // 通知
    Topic topic = topicService.selectById(topicId);
    topic.setCollectCount(topic.getCollectCount() + 1);
    topicService.update(topic);
    // 收藏自己的话题不发通知
    if (!user.getId().equals(topic.getUserId())) {
      notificationService.insert(user.getId(), topic.getUserId(), topicId, "COLLECT", null);
      // 发送邮件通知
      String emailTitle = "你的话题 %s 被 %s 收藏了，快去看看吧！";
      // 如果开启了websocket，就发网页通知
      if (systemConfigService.selectAllConfig().get("websocket").toString().equals("1")
          && Constants.usernameSocketIdMap.containsKey(topic.getUserId())) {
        Constants.websocketUserMap.get(Constants.usernameSocketIdMap.get(topic.getUserId())).getClient()
            .sendEvent("notifications", String.format(emailTitle, topic.getTitle(), user.getUsername()));
      }
      User targetUser = userService.selectById(topic.getUserId());
      if (!StringUtils.isEmpty(targetUser.getEmail()) && targetUser.getEmailNotification()) {
        String emailContent = "<a href='%s/notifications' target='_blank'>传送门</a>";
        new Thread(() -> emailService.sendEmail(
            targetUser.getEmail(),
            String.format(emailTitle, topic.getTitle(), user.getUsername()),
            String.format(emailContent, systemConfigService.selectAllConfig().get("base_url").toString()))).start();
      }
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
    // 对话题中冗余的collectCount字段收藏数量-1
    Topic topic = topicService.selectById(topicId);
    topic.setCollectCount(topic.getCollectCount() - 1);
    topicService.update(topic);
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
  public MyPage<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize) {
    MyPage<Map<String, Object>> iPage = new MyPage<>(pageNo,
        pageSize == null ?
            Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString()) : pageSize
    );
    iPage = collectMapper.selectByUserId(iPage, userId);
    topicService.selectTags(iPage, topicTagService, tagService);
    return iPage;
  }
}
