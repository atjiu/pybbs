package co.yiiu.web.admin;

import co.yiiu.core.base.BaseController;
import co.yiiu.module.node.model.Node;
import co.yiiu.module.node.service.NodeService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/topic")
public class TopicAdminController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private NodeService nodeService;

  /**
   * topic list
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Integer p, Model model) {
    model.addAttribute("p", p);
    return "admin/topic/list";
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

    model.addAttribute("topic", topic);
    model.addAttribute("from", "end");
    return "front/topic/edit";
  }

  /**
   * 更新话题
   *
   * @param title
   * @param content
   * @return
   */
  @PostMapping("/{id}/edit")
  public String update(@PathVariable Integer id, Integer nodeId, String title, String url, String content,
                       HttpServletResponse response) throws Exception {
    Topic topic = topicService.findById(id);
    Node oldNode = topic.getNode();

    Node node = nodeService.findById(nodeId);
    if (node == null) throw new Exception("版块不存在");
    if (!StringUtils.isEmpty(url) && !url.contains("http://") && !url.contains("https://"))
      throw new Exception("转载URL格式不正确");

    //更新node的话题数
    if (!Objects.equals(topic.getNode().getId(), nodeId)) {
      nodeService.dealTopicCount(topic.getNode(), -1);
      nodeService.dealTopicCount(node, 1);
    }

    topic.setNode(node);
    topic.setTitle(title);
    topic.setUrl(url);
    topic.setContent(content);
    topic.setModifyTime(new Date());
    topicService.save(topic);

    return redirect(response, "/topic/" + topic.getId());
  }

  /**
   * delete topic
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/delete")
  public String delete(String a, HttpServletResponse response, @PathVariable Integer id) {
    // delete topic
    topicService.deleteById(id);
    return redirect(response, StringUtils.isEmpty(a) ? "/" : "/admin/topic/list");
  }

  /**
   * 加/减精华
   *
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/good")
  public String good(@PathVariable Integer id, HttpServletResponse response) {
    Topic topic = topicService.findById(id);
    topic.setGood(!topic.isGood());
    topicService.save(topic);
    return redirect(response, "/topic/" + id);
  }

  /**
   * 置/不置顶
   *
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/top")
  public String top(@PathVariable Integer id, HttpServletResponse response) {
    Topic topic = topicService.findById(id);
    topic.setTop(!topic.isTop());
    topicService.save(topic);
    return redirect(response, "/topic/" + id);
  }

  /**
   * 锁定/不锁定
   *
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/lock")
  public String lock(@PathVariable Integer id, HttpServletResponse response) {
    Topic topic = topicService.findById(id);
    topic.setLock(!topic.isLock());
    topicService.save(topic);
    return redirect(response, "/topic/" + id);
  }
}
