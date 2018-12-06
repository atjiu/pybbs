package co.yiiu.pybbs.controller.admin;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController extends BaseAdminController {

  @RequiresAuthentication
  @GetMapping({"/", "/index"})
  public String index() {
    return "admin/index";
  }

}
