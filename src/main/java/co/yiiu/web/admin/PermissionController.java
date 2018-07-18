package co.yiiu.web.admin;

import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.service.PermissionService;
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
@RequestMapping("/admin/permission")
public class PermissionController extends BaseController {

  @Autowired
  private PermissionService permissionService;

  @GetMapping("/list")
  public String list(Model model) {
    List<Map<String, Object>> node = permissionService.findAll();
    model.addAttribute("data", new Gson().toJson(node));
    return "admin/permission/list";
  }

  @PostMapping("/add")
  @ResponseBody
  public Result add(Integer id, String name, String value, String url, @RequestParam(defaultValue = "0") Integer pid) {
    Permission permission = new Permission();
    permission.setId(id);
    permission.setName(name);
    permission.setValue(value);
    permission.setPid(pid);
    permission.setUrl(url);
    permissionService.save(permission);
    return Result.success();
  }

  @GetMapping("/delete")
  @ResponseBody
  public Result delete(Integer id) {
    permissionService.delete(id);
    return Result.success();
  }

}
