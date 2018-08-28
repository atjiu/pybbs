package co.yiiu.web.api;

import co.yiiu.config.properties.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.module.es.service.TagSearchService;
import co.yiiu.module.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
  @Autowired
  private TagService tagService;
  @Autowired
  private SiteConfig siteConfig;

  /**
   * 标签输入自动完成
   *
   * @param keyword 输入的内容
   * @return
   */
  @GetMapping("/autocomplete")
  public Result autocomplete(String keyword) {
    if (StringUtils.isEmpty(keyword)) return Result.success();
    if (siteConfig.isSearch()) {
      return Result.success(tagSearchService.query(keyword, 7));
    } else {
      return Result.success(tagService.findByNameLike(keyword, 0, 7));
    }
  }

}
