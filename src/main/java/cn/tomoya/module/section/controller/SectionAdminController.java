package cn.tomoya.module.section.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.module.section.entity.Section;
import cn.tomoya.module.section.service.SectionService;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/admin/section")
public class SectionAdminController extends BaseController {

  @Autowired
  private SectionService sectionService;

  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("sections", sectionService.findAll());
    return render("/admin/section/list");
  }

  @GetMapping("/add")
  public String add(Model model) {
    return render("/admin/section/add");
  }

  @PostMapping("/add")
  public String save(String name, HttpServletResponse response) {
    Section section = new Section();
    section.setName(name);
    sectionService.save(section);
    sectionService.clearCache();
    return redirect(response, "/admin/section/list");
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable int id, Model model) {
    model.addAttribute("section", sectionService.findOne(id));
    return render("/admin/section/edit");
  }

  @PostMapping("/{id}/edit")
  public String update(@PathVariable int id, Model model, HttpServletResponse response, String name) {
    Section section = sectionService.findOne(id);
    section.setName(name);
    sectionService.save(section);
    sectionService.clearCache();
    return redirect(response, "/admin/section/list");
  }

  @GetMapping("/{id}/delete")
  public String delete(@PathVariable int id, HttpServletResponse response) {
    sectionService.delete(id);
    sectionService.clearCache();
    return redirect(response, "/admin/section/list");
  }
}
