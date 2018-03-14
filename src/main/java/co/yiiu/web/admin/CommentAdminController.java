package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.module.comment.model.Comment;
import co.yiiu.module.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController extends BaseController {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private CommentService commentService;

  /**
   * 评论列表
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Integer p, Model model) {
    Page<Comment> page = commentService.page(p == null ? 1 : p, siteConfig.getPageSize());
    model.addAttribute("page", page);
    return "admin/comment/list";
  }

  /**
   * 编辑评论
   *
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Integer id, Model model) throws Exception {
    if (getUser().isBlock()) throw new Exception("你的帐户已经被禁用，不能进行此项操作");

    Comment comment = commentService.findById(id);
    model.addAttribute("comment", comment);
    return "admin/comment/edit";
  }

  /**
   * 更新评论内容
   *
   * @param id
   * @param topicId
   * @param content
   * @param response
   * @return
   */
  @PostMapping("/update")
  public String update(Integer id, Integer topicId, String content, HttpServletResponse response) throws Exception {
    if (getUser().isBlock()) throw new Exception("你的帐户已经被禁用，不能进行此项操作");

    Comment comment = commentService.findById(id);
    if (comment == null) throw new Exception("评论不存在");

    comment.setContent(content);
    commentService.save(comment);
    return redirect(response, "/topic/" + topicId);
  }

  /**
   * 删除评论
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/delete")
  public String delete(@PathVariable Integer id, HttpServletResponse response) {
    if (id != null) {
      Map map = commentService.delete(id);
      return redirect(response, "/topic/" + map.get("topicId"));
    }
    return redirect(response, "/");
  }

}
