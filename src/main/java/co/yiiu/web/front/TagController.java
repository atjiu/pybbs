package co.yiiu.web.front;

import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.module.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tomoya at 2018/3/28
 */
@Controller
@RequestMapping("/tag")
public class TagController extends BaseController {

  @Autowired
  private TagService tagService;

  @GetMapping("/autocomplete")
  @ResponseBody
  public Result autocomplete(String keyword) {
    return Result.success(tagService.findByNameLike(keyword));
  }

}
