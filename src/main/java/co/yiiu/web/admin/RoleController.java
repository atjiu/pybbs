package co.yiiu.web.admin;

import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.model.RolePermission;
import co.yiiu.module.security.service.PermissionService;
import co.yiiu.module.security.service.RolePermissionService;
import co.yiiu.module.security.service.RoleService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya at 2018/3/20
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

  @Autowired
  private RoleService roleService;
  @Autowired
  private PermissionService permissionService;
  @Autowired
  private RolePermissionService rolePermissionService;

  @GetMapping("/list")
  public String list(Model model) {
    List<Role> roles = roleService.findAll();
    model.addAttribute("roles", roles);
    return "admin/role/list";
  }

  @GetMapping("/add")
  public String add(Model model) {
    List<Map<String, Object>> node = permissionService.findAll();
    model.addAttribute("data", new Gson().toJson(node));
    return "admin/role/add";
  }

  @PostMapping("/add")
  @ResponseBody
  public Result save(@RequestParam("nodeIds[]") Integer[] nodeIds, String name) {
    roleService.save(null, name, nodeIds);
    return Result.success();
  }

  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    Role role = roleService.findById(id);
    List<Map<String, Object>> node = permissionService.findAll();
    List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(id);
    model.addAttribute("role", role);
    model.addAttribute("data", new Gson().toJson(node));
    model.addAttribute("rolePermissions", rolePermissions);
    return "admin/role/edit";
  }

  @PostMapping("/edit")
  @ResponseBody
  public Result update(Integer id, @RequestParam("nodeIds[]") Integer[] nodeIds, String name) {
    roleService.save(id, name, nodeIds);
    return Result.success();
  }

  @GetMapping("/delete")
  public String delete(Integer id) {
    roleService.delete(id);
    return redirect("/admin/role/list");
  }
}
