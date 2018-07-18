package co.yiiu.web.api;

import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.EnumUtil;
import co.yiiu.module.comment.model.Comment;
import co.yiiu.module.comment.service.CommentService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.model.VoteAction;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.ReputationPermission;
import co.yiiu.module.user.model.User;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by tomoya at 2018/4/12
 */
@RestController
@RequestMapping("/api/comment")
public class CommentApiController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private CommentService commentService;

  /**
   * 话题的评论列表
   * @param topicId 话题ID
   * @return
   */
  @GetMapping("/list")
  public Result list(Integer topicId) {
    return Result.success(commentService.findCommentWithTopic(topicId));
  }

  /**
   * 保存评论
   * @param topicId 话题ID
   * @param commentId 回复的评论ID，可以为null
   * @param content 评论内容
   * @return
   */
  @PostMapping("/save")
  public Result save(Integer topicId, Integer commentId, String content) {
    User user = getApiUser();
    ApiAssert.notTrue(user.getBlock(), "你的帐户已经被禁用，不能进行此项操作");
    ApiAssert.notEmpty(content, "评论内容不能为空");
    ApiAssert.notNull(topicId, "话题ID不存在");

    Topic topic = topicService.findById(topicId);
    ApiAssert.notNull(topic, "回复的话题不存在");

    Comment comment = commentService.createComment(user.getId(), topic, commentId, content);
    return Result.success(comment);
  }

  /**
   * 对评论进行编辑
   * @param id 评论ID
   * @param content 评论内容
   * @return
   */
  @PostMapping("/edit")
  public Result edit(Integer id, String content) {
    User user = getApiUser();
    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.EDIT_COMMENT.getReputation(), "声望太低，不能进行这项操作");
    ApiAssert.notEmpty(content, "评论内容不能为空");
    Comment comment = commentService.findById(id);
    Comment oldComment = comment;
    comment.setContent(Jsoup.clean(content, Whitelist.relaxed()));
    commentService.save(comment);
    Topic topic = topicService.findById(comment.getTopicId());
    comment = commentService.update(topic, oldComment, comment, user.getId());
    return Result.success(comment);
  }

  /**
   * 对评论投票
   *
   * @param id 评论ID
   * @param action 评论动作，只能填 UP, DOWN
   * @return
   */
  @GetMapping("/{id}/vote")
  public Result vote(@PathVariable Integer id, String action) {
    User user = getApiUser();
    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.VOTE_COMMENT.getReputation(), "声望太低，不能进行这项操作");
    Comment comment = commentService.findById(id);

    ApiAssert.notNull(comment, "评论不存在");
    ApiAssert.notTrue(user.getId().equals(comment.getUserId()), "不能给自己的评论投票");
    ApiAssert.isTrue(EnumUtil.isDefined(VoteAction.values(), action), "参数错误");

    Map<String, Object> map = commentService.vote(user.getId(), comment, action);
    return Result.success(map);
  }
}
