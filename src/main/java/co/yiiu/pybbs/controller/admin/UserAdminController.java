package co.yiiu.pybbs.controller.admin;

import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.Result;
import co.yiiu.pybbs.util.StringUtil;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController extends BaseAdminController {

  @Autowired
  private UserService userService;

  // 前台用户管理
  @RequiresPermissions("user:list")
  @GetMapping("/list")
  public String list(@RequestParam(defaultValue = "1") Integer pageNo, Model model){
    IPage<User> iPage = userService.selectAll(pageNo);
    model.addAttribute("page", iPage);
    return "admin/user/list";
  }

  // 编辑用户
  @RequiresPermissions("user:edit")
  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    model.addAttribute("user", userService.selectById(id));
    return "admin/user/edit";
  }

  // 保存编辑后的用户信息
  @RequiresPermissions("user:edit")
  @PostMapping("/edit")
  @ResponseBody
  public Result update(User user) {
    // 如果密码不为空，给加密一下再保存
    if (!StringUtils.isEmpty(user.getPassword())) {
      user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    } else {
      user.setPassword(null);
    }
    // 如果邮件接收通知没有勾选，user对象里的属性就是null，手动设置成false
    if (user.getEmailNotification() == null) user.setEmailNotification(false);
    userService.update(user);
    return success();
  }

  // 删除用户
  @RequiresPermissions("user:delete")
  @GetMapping("/delete")
  @ResponseBody
  public Result delete(Integer id) {
    userService.deleteUser(id);
    return success();
  }

  // 刷新token
  @RequiresPermissions("user:refresh_token")
  @GetMapping("/refreshToken")
  @ResponseBody
  public Result refreshToken(Integer id) {
    User user = userService.selectById(id);
    user.setToken(StringUtil.uuid());
    userService.update(user);
    return success(user);
  }
}
