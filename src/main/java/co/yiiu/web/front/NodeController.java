package co.yiiu.web.front;

import co.yiiu.core.base.BaseController;
import co.yiiu.module.node.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
public class NodeController extends BaseController {

  @Autowired
  private NodeService nodeService;

  @GetMapping("/go/{value}")
  public String topics(@PathVariable String value, Integer p, Model model) {
    model.addAttribute("p", p);
    model.addAttribute("value", value);
    model.addAttribute("node", nodeService.findByValue(value));
    return "front/node/list";
  }

}
