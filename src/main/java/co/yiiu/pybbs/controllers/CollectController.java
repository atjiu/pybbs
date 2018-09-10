package co.yiiu.pybbs.controllers;

import co.yiiu.pybbs.exceptions.ApiAssert;
import co.yiiu.pybbs.models.Collect;
import co.yiiu.pybbs.models.Topic;
import co.yiiu.pybbs.models.User;
import co.yiiu.pybbs.services.CollectService;
import co.yiiu.pybbs.services.TopicService;
import co.yiiu.pybbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController {

  @Autowired
  private CollectService collectService;
  @Autowired
  private TopicService topicService;

  @PostMapping("save")
  public Result save(String topicId) {
    ApiAssert.notEmpty(topicId, "要收藏的话题ID不能为空");
    User user = getUser();
    Topic topic = topicService.findById(topicId);
    ApiAssert.notNull(topic, "要收藏的话题不存在");
    Collect collect = collectService.findByUserIdAndTopicId(user.getId(), topicId);
    ApiAssert.isNull(collect, "您已经收藏了这个话题");
    collect = new Collect();
    collect.setTopicId(topicId);
    collect.setUserId(user.getId());
    collect.setInTime(new Date());
    collectService.save(collect);
    // 更新话题的收藏数
    topic.setCollectCount(topic.getCollectCount() + 1);
    topicService.save(topic);
    return Result.success();
  }

  @PostMapping("delete")
  public Result delete(String topicId) {
    ApiAssert.notEmpty(topicId, "要取消收藏的话题ID不能为空");
    User user = getUser();
    Topic topic = topicService.findById(topicId);
    ApiAssert.notNull(topic, "要取消收藏的话题不存在");
    Collect collect = collectService.findByUserIdAndTopicId(user.getId(), topicId);
    ApiAssert.notNull(collect, "您还没有收藏了这个话题");
    // 更新话题的收藏数
    topic.setCollectCount(topic.getCollectCount() - 1);
    topicService.save(topic);
    // 取消收藏
    collectService.deleteById(collect.getId());
    return Result.success();
  }

}
