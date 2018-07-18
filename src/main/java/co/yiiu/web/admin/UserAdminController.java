package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.security.crypto.BCryptPasswordEncoder;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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

  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    model.addAttribute("user", userService.findById(id));
    return "admin/user/edit";
  }

  @PostMapping("/edit")
  @ResponseBody
  public Result update(Integer id, String username, String password, Integer reputation) {
    ApiAssert.notEmpty(username, "用户名不能为空");
    ApiAssert.notNull(reputation, "声望不能为空");
    User user = userService.findById(id);
    user.setUsername(username);
    // 如果密码字段存在，则修改
    if(!StringUtils.isEmpty(password)) {
      user.setPassword(new BCryptPasswordEncoder().encode(password));
    }
    user.setReputation(reputation);
    userService.save(user);
    return Result.success();
  }

  @GetMapping("/block")
  @ResponseBody
  public Result block(Integer id) {
    userService.blockUser(id);
    return Result.success();
  }

  @GetMapping("/delete")
  @ResponseBody
  public Result delete(Integer id) {
    userService.deleteById(id);
    return Result.success();
  }

}
