package co.yiiu.web.api;

import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.module.es.service.TagSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomoya at 2018/4/12
 */
@RestController
@RequestMapping("/api/tag")
public class TagApiController extends BaseController {

  @Autowired
  private TagSearchService tagSearchService;

  /**
   * 标签输入自动完成
   * @param keyword 输入的内容
   * @return
   */
  @GetMapping("/autocomplete")
  public Result autocomplete(String keyword) {
    return Result.success(tagSearchService.query(keyword, 7));
  }

}
