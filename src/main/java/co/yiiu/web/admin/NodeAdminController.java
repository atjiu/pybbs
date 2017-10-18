package co.yiiu.web.admin;

import co.yiiu.core.base.BaseController;
import co.yiiu.module.node.model.Node;
import co.yiiu.module.node.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/node")
public class NodeAdminController extends BaseController {

  @Autowired
  private NodeService nodeService;

  @GetMapping("/list")
  public String list(Model model, Integer pid) {
    model.addAttribute("pnodes", nodeService.findByPid(0));
    if(pid == null) {
      model.addAttribute("nodes", nodeService.findAllChild());
    } else {
      model.addAttribute("pid", pid);
      model.addAttribute("nodes", nodeService.findByPid(pid));
    }
    return render("/admin/node/list");
  }

  @GetMapping("/add")
  public String add(Model model) {
    model.addAttribute("pnodes", nodeService.findByPid(0));
    return render("/admin/node/add");
  }

  @PostMapping("/add")
  public String save(Integer pid, String name, String value, String intro) {
    Node node = new Node();
    node.setName(name);
    node.setPid(pid);
    node.setValue(value);
    node.setTopicCount(0);
    node.setIntro(intro);
    node.setInTime(new Date());
    nodeService.save(node);
    return redirect("/admin/node/list");
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable int id, Model model) {
    model.addAttribute("pnodes", nodeService.findByPid(0));
    model.addAttribute("node", nodeService.findById(id));
    return render("/admin/node/edit");
  }

  @PostMapping("/{id}/edit")
  public String update(@PathVariable int id, String name, Integer pid, String value, String intro, Integer topicCount,
                       HttpServletResponse response) {
    Node node = nodeService.findById(id);
    node.setName(name);
    node.setPid(pid);
    node.setTopicCount(topicCount);
    node.setValue(value);
    node.setIntro(intro);
    nodeService.save(node);
    return redirect(response, "/admin/node/list");
  }

  @GetMapping("/{id}/delete")
  public String delete(@PathVariable int id, HttpServletResponse response) {
    nodeService.delete(id);
    return redirect(response, "/admin/node/list");
  }
}
