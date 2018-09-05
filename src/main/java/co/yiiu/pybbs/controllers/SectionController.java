package co.yiiu.pybbs.controllers;

import co.yiiu.pybbs.conf.properties.SiteConfig;
import co.yiiu.pybbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/section")
public class SectionController extends BaseController {

  @Autowired
  private SiteConfig siteConfig;

  @GetMapping("/")
  public Result sections() {
    return Result.success(siteConfig.getSections());
  }
}
