package cn.tomoya.module.node.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.exception.Result;
import cn.tomoya.module.node.entity.Label;
import cn.tomoya.module.node.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya on 2017/6/16.
 */
@Controller
@RequestMapping("/label")
public class LabelController extends BaseController {

  @Autowired
  private LabelService labelService;

  /**
   * all node page
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Integer p, Model model) {
    model.addAttribute("p", p);
    return render("/front/label/list");
  }

  /**
   * fuzzy query by name
   *
   * @param term
   * @return json
   */
  @GetMapping("/search")
  @ResponseBody
  public List<String> search(String term) {
    List<String> labels1 = new ArrayList<>();
    if (!StringUtils.isEmpty(term)) {
      List<Label> labels = labelService.findByNameLike(term);
      if (labels != null) {
        for (Label label : labels) {
          labels1.add(label.getName());
        }
      }
    }
    return labels1;
  }

}
