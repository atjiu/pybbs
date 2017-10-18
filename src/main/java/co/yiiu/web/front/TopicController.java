package co.yiiu.web.front;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.util.DateUtil;
import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.node.model.Node;
import co.yiiu.module.node.service.NodeService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private NodeService nodeService;
  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;

  /**
   * 话题详情
   *
   * @param id
   * @param model
   * @return
   * @throws Exception
   */
  @GetMapping("/{id}")
  public String detail(@PathVariable Integer id, Model model)
      throws Exception {
    Topic topic = topicService.findById(id);
    if (topic == null)
      throw new Exception("话题不存在");

    // 浏览量+1
    topic.setView(topic.getView() + 1);
    topicService.save(topic);// 更新话题数据
    model.addAttribute("topic", topic);
    // 查询是否收藏过
    model.addAttribute("collect", collectService.findByUserAndTopic(getUser(), topic));
    // 查询这个话题被收藏的个数
    model.addAttribute("collectCount", collectService.countByTopic(topic));
    return "/front/topic/detail";
  }

  /**
   * 创建话题
   *
   * @return
   */
  @GetMapping("/create")
  public String create() throws Exception {
    User user = getUser();

    if (user.isBlock()) throw new Exception("你的帐户已经被禁用了，不能进行此项操作");

    if (user.getScore() < 10) throw new Exception("你的积分不足，不能发布话题");

    String now = DateUtil.formatDate(new Date());
    Date date1 = DateUtil.string2Date(now + " 00:00:00", DateUtil.FORMAT_DATETIME);
    Date date2 = DateUtil.string2Date(now + " 23:59:59", DateUtil.FORMAT_DATETIME);
    if (siteConfig.getMaxCreateTopic() < topicService.countByInTimeBetween(date1, date2))
      throw new Exception("你今天发布的话题超过系统设置的最大值，请明天再发");

    return "front/topic/create";
  }

  /**
   * 保存话题
   *
   * @param title
   * @param content
   * @param nodeId
   * @return
   */
  @PostMapping("/save")
  public String save(Integer nodeId, String title, String content,
                     HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
    User user = getUser();

    if (user.isBlock()) throw new Exception("你的帐户已经被禁用了，不能进行此项操作");

    if (user.getScore() < 10) throw new Exception("你的积分不足，不能发布话题");

    Date now = new Date();
    String currentDateStr = DateUtil.formatDate(now);
    Date date1 = DateUtil.string2Date(currentDateStr + " 00:00:00", DateUtil.FORMAT_DATETIME);
    Date date2 = DateUtil.string2Date(currentDateStr + " 23:59:59", DateUtil.FORMAT_DATETIME);
    if (siteConfig.getMaxCreateTopic() <= topicService.countByInTimeBetween(date1, date2))
      throw new Exception("你今天发布的话题超过系统设置的最大值，请明天再发");

    Node node = nodeService.findById(nodeId);
    String errorMessage;
    if (StringUtils.isEmpty(title)) {
      errorMessage = "请输入标题";
    } else if (node == null) {
      errorMessage = "节点不存在";
    } else {
      if (topicService.findByTitle(title) != null) throw new Exception("话题标题已经存在");

      Topic topic = new Topic();

      topic.setNode(node);
      topic.setTitle(title);
      topic.setContent(content);
      topic.setInTime(new Date());
      topic.setView(0);
      topic.setUser(user);
      topic.setGood(false);
      topic.setTop(false);
      topic.setLock(false);
      topicService.save(topic);

      // update score
      user.setScore(user.getScore() - 10);
      userService.save(user);

      return redirect(response, "/topic/" + topic.getId());
    }
    redirectAttributes.addFlashAttribute("node", node);
    redirectAttributes.addFlashAttribute("title", title);
    redirectAttributes.addFlashAttribute("content", content);
    redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
    return redirect("/topic/create");
  }

  /**
   * 编辑话题
   *
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable int id, Model model) throws Exception {
    Topic topic = topicService.findById(id);
    if (topic == null) throw new Exception("话题不存在");

    if (DateUtil.isExpire(DateUtil.getMinuteAfter(topic.getInTime(), 5)))
      throw new Exception("话题发布到现在已经超过5分钟，你不能再编辑了");

    model.addAttribute("topic", topic);
    return "/front/topic/edit";
  }

  /**
   * 更新话题
   *
   * @param title
   * @param content
   * @return
   */
  @PostMapping("/{id}/edit")
  public String update(@PathVariable Integer id, Integer nodeId, String title, String content,
                       HttpServletResponse response) throws Exception {
    Topic topic = topicService.findById(id);
    User user = getUser();

    if (DateUtil.isExpire(DateUtil.getMinuteAfter(topic.getInTime(), 5)))
      throw new Exception("话题发布到现在已经超过5分钟，你不能再编辑了");

    if (topic.getUser().getId() != user.getId())
      throw new Exception("非法操作");

    Node node = nodeService.findById(nodeId);
    if (node == null)
      throw new Exception("版块不存在");

    topic.setNode(node);
    topic.setTitle(title);
    topic.setContent(content);
    topic.setModifyTime(new Date());
    topicService.save(topic);
    return redirect(response, "/topic/" + topic.getId());
  }

}
