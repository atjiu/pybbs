package co.yiiu.pybbs.controllers;

import co.yiiu.pybbs.conf.properties.SiteConfig;
import co.yiiu.pybbs.exceptions.ApiAssert;
import co.yiiu.pybbs.models.AccessToken;
import co.yiiu.pybbs.models.Comment;
import co.yiiu.pybbs.models.Topic;
import co.yiiu.pybbs.models.User;
import co.yiiu.pybbs.services.CollectService;
import co.yiiu.pybbs.services.CommentService;
import co.yiiu.pybbs.services.TopicService;
import co.yiiu.pybbs.services.UserService;
import co.yiiu.pybbs.utils.MDUtil;
import co.yiiu.pybbs.utils.Result;
import co.yiiu.pybbs.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya at 2018/9/4
 */
@RestController
@RequestMapping("/topic")
public class TopicController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private UserService userService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private StringUtil stringUtil;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private CollectService collectService;

  @GetMapping("/detail")
  public Result detail(String id, @RequestParam(defaultValue = "false") Boolean mdrender) {
    Map<String, Object> map = new HashMap<>();
    ApiAssert.notEmpty(id, "话题ID不存在");

    // 查询topic
    Topic topic = topicService.findById(id);

    // 更新话题点击次数
    topic.setView(topic.getView() + 1);
    topicService.save(topic);

    // 根据mdrender判断内容是否要被渲染成html内容
    if (mdrender) topic.setContent(Jsoup.clean(MDUtil.render(topic.getContent()), Whitelist.relaxed()));

    // 判断用户是否登录过，登录过的话，查询出来这个话题是否被收藏过
    User user = getUserForTopicDetail();
    if (user != null && collectService.findByUserIdAndTopicId(user.getId(), id) != null) {
      map.put("collect", true);
    } else {
      map.put("collect", false);
    }

    // 查询话题的user
    User topicUser = userService.findById(topic.getUserId());
    topic.setUser(topicUser);

    // 查询话题的评论
    List<Comment> comments = commentService.findByTopicId(id);
    comments.forEach(comment -> {
      comment.setUser(userService.findById(comment.getUserId()));
      if (comment.getCommentId() != null) comment.setComment(commentService.findById(comment.getCommentId()));
    });

    map.put("topic", topic);
    map.put("comments", comments);
    return Result.success(map);
  }

  @PostMapping("/create")
  public Result create(String title, String content, String tab) {
    User user = getUser();
    ApiAssert.notEmpty(title, "标题不能为空");
//    ApiAssert.notEmpty(content, "内容不能为空");
    ApiAssert.notEmpty(tab, "分类不能为空");
    ApiAssert.isTrue(stringUtil.sectionValues().contains(tab), "分类不存在");
    Topic topic = topicService.findByTitle(title);
    ApiAssert.isNull(topic, "话题标题已经存在");
    topic = new Topic();
    topic.setUserId(user.getId());
    topic.setTitle(Jsoup.clean(title, Whitelist.none()));
    topic.setContent(content);
    topic.setInTime(new Date());
    topic.setCommentCount(0);
    topic.setCollectCount(0);
    topic.setTab(tab);
    topic.setView(0);
    topic.setGood(false);
    topic.setTop(false);
    topicService.save(topic);
    //更新用户的积分
    user.setScore(user.getScore() + siteConfig.getCreateTopicScore());
    userService.save(user);
    return Result.success(topic.getId());
  }

  @PostMapping("/update")
  public Result update(String id, String title, String content, String tab) {
    ApiAssert.notEmpty(id, "话题ID不能为空");
    ApiAssert.notEmpty(title, "话题标题不能为空");
//    ApiAssert.notEmpty(content, "话题内容不能为空");
    ApiAssert.notEmpty(tab, "话题分类不能为空");
    ApiAssert.isTrue(stringUtil.sectionValues().contains(tab), "分类不存在");
    User user = getUser();
    Topic topic = topicService.findById(id);
    ApiAssert.notNull(topic, "话题不存在");
    ApiAssert.isTrue(user.getId().equals(topic.getUserId())
        || siteConfig.getAdmin().contains(user.getUsername()), "不能修改别人的话题");
    topic.setTitle(Jsoup.clean(title, Whitelist.none()));
    topic.setContent(content);
    topic.setTab(tab);
    topic.setModifyTime(new Date());
    topicService.save(topic);
    return Result.success();
  }

  @GetMapping("/delete")
  public Result delete(String id) {
    User user = getUser();
    Topic topic = topicService.findById(id);
    ApiAssert.notTrue(topic == null, "您要删除的话题不存在");
    ApiAssert.isTrue(user.getId().equals(topic.getUserId())
        || siteConfig.getAdmin().contains(user.getUsername()), "不能删除别人的话题");
    topicService.deleteById(id);
    //更新用户的积分
    user.setScore(user.getScore() - siteConfig.getCreateTopicScore());
    userService.save(user);
    return Result.success();
  }

  @PostMapping("/good")
  public Result good(String id){
    User user = getUser();
    Topic topic = topicService.findById(id);
    ApiAssert.notTrue(topic == null, "您要加精/取消加精的话题不存在");
    ApiAssert.isTrue(siteConfig.getAdmin().contains(user.getUsername()), "您没有权限");
    topic.setGood(!topic.getGood());
    topicService.save(topic);
    //更新用户的积分
    if (topic.getGood()) {
      user.setScore(user.getScore() + siteConfig.getGoodTopicScore());
    } else {
      user.setScore(user.getScore() - siteConfig.getGoodTopicScore());
    }
    userService.save(user);
    return Result.success();
  }

  @PostMapping("/top")
  public Result top(String id){
    User user = getUser();
    Topic topic = topicService.findById(id);
    ApiAssert.notTrue(topic == null, "您要置顶/取消置顶的话题不存在");
    ApiAssert.isTrue(siteConfig.getAdmin().contains(user.getUsername()), "您没有权限");
    topic.setTop(!topic.getTop());
    topicService.save(topic);
    return Result.success();
  }
}
