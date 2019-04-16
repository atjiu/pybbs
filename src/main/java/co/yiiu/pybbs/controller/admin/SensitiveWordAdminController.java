package co.yiiu.pybbs.controller.admin;

import co.yiiu.pybbs.model.SensitiveWord;
import co.yiiu.pybbs.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/sensitive_word")
public class SensitiveWordAdminController extends BaseAdminController {

  @Autowired
  private SensitiveWordService sensitiveWordService;

  @GetMapping("/list")
  public String list() {

    return null;
  }

  @PostMapping("/save")
  public String save(String word) {

    return null;
  }

  @PostMapping("/update")
  public String update(String word) {

    return null;
  }

  @PostMapping("/delete")
  public String delete(Integer id) {

    return null;
  }
}
