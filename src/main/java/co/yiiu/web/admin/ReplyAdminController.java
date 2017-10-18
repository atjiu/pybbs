package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.module.reply.model.Reply;
import co.yiiu.module.reply.service.ReplyService;
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
@RequestMapping("/admin/reply")
public class ReplyAdminController extends BaseController {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private ReplyService replyService;

  /**
   * 回复列表
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Integer p, Model model) {
    Page<Reply> page = replyService.page(p == null ? 1 : p, siteConfig.getPageSize());
    model.addAttribute("page", page);
    return "admin/reply/list";
  }

  /**
   * 编辑回复
   *
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Integer id, Model model) throws Exception {
    if (getUser().isBlock()) throw new Exception("你的帐户已经被禁用，不能进行此项操作");

    Reply reply = replyService.findById(id);
    model.addAttribute("reply", reply);
    return "admin/reply/edit";
  }

  /**
   * 更新回复内容
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

    Reply reply = replyService.findById(id);
    if (reply == null) throw new Exception("回复不存在");

    reply.setContent(content);
    replyService.save(reply);
    return redirect(response, "/topic/" + topicId);
  }

  /**
   * 删除回复
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/delete")
  public String delete(@PathVariable Integer id, HttpServletResponse response) {
    if (id != null) {
      Map map = replyService.delete(id);
      return redirect(response, "/topic/" + map.get("topicId"));
    }
    return redirect(response, "/");
  }

}
