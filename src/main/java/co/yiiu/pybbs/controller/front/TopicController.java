package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.model.Collect;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.CollectService;
import co.yiiu.pybbs.service.TagService;
import co.yiiu.pybbs.service.TopicService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private TagService tagService;
  @Autowired
  private UserService userService;
  @Autowired
  private CollectService collectService;

  // 话题详情
  @GetMapping("/{id}")
  public String detail(@PathVariable Integer id, Model model, HttpServletRequest request) {
    // 查询话题详情
    Topic topic = topicService.selectById(id);
    Assert.notNull(topic, "话题不存在");
    // 查询话题关联的标签
    List<Tag> tags = tagService.selectByTopicId(id);
    // 查询话题的作者信息
    User topicUser = userService.selectById(topic.getUserId());
    // 查询话题有多少收藏
    List<Collect> collects = collectService.selectByTopicId(id);
    // 如果自己登录了，查询自己是否收藏过这个话题
    if (getUser() != null) {
      Collect collect = collectService.selectByTopicIdAndUserId(id, getUser().getId());
      model.addAttribute("collect", collect);
    }
    // 话题浏览量+1
    topic = topicService.addViewCount(topic, request);

    model.addAttribute("topic", topic);
    model.addAttribute("tags", tags);
    model.addAttribute("topicUser", topicUser);
    model.addAttribute("collects", collects);
    return render("topic/detail");
  }

  @GetMapping("/create")
  public String create(String tag, Model model) {
    model.addAttribute("tag", tag);
    return render("topic/create");
  }

  // 编辑话题
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    Topic topic = topicService.selectById(id);
    Assert.isTrue(topic.getUserId().equals(getUser().getId()), "谁给你的权限修改别人的话题的？");
    // 查询话题的标签
    List<Tag> tagList = tagService.selectByTopicId(id);
    // 将标签集合转成逗号隔开的字符串
    String tags = StringUtils.collectionToCommaDelimitedString(tagList.stream().map(Tag::getName).collect(Collectors.toList()));

    model.addAttribute("topic", topic);
    model.addAttribute("tags", tags);
    return render("topic/edit");
  }

  @GetMapping("/tag/{name}")
  public String tag(@PathVariable String name, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    Tag tag = tagService.selectByName(name);
    Assert.notNull(tag, "标签不存在");
    // 查询标签关联的话题
    MyPage<Map<String, Object>> iPage = tagService.selectTopicByTagId(tag.getId(), pageNo);
    model.addAttribute("tag", tag);
    model.addAttribute("page", iPage);
    return render("tag/tag");
  }
}
