package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tomoya at 2018/3/26
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;

  @GetMapping("/list")
  public String list(@RequestParam(defaultValue = "1") Integer p, Model model) {
    model.addAttribute("page", userService.pageUser(p, siteConfig.getPageSize()));
    return "admin/user/list";
  }

  @GetMapping("/delete")
  @ResponseBody
  public Result delete(Integer id) {
    userService.deleteById(id);
    return Result.success();
  }

}
