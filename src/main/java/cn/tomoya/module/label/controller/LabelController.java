package cn.tomoya.module.label.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.module.label.entity.Label;
import cn.tomoya.module.label.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomoya on 2017/6/16.
 */
@Controller
@RequestMapping("/label")
public class LabelController extends BaseController {

  @Autowired
  private LabelService labelService;

  /**
   * all label page
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

  /**
   * query topic by label page
   * @param name
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/{name}/topic")
  public String labelTopic(@PathVariable String name, Integer p, Model model) {
    Label label = labelService.findByName(name);
    model.addAttribute("label", label);
    model.addAttribute("p", p);
    return render("/front/label/topic");
  }

}
