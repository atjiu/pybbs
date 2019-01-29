package co.yiiu.pybbs.controller.admin;

import co.yiiu.pybbs.service.CommentService;
import co.yiiu.pybbs.service.TagService;
import co.yiiu.pybbs.service.TopicService;
import co.yiiu.pybbs.service.UserService;
import com.sun.management.OperatingSystemMXBean;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.management.ManagementFactory;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController extends BaseAdminController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private TagService tagService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private UserService userService;

  @RequiresAuthentication
  @GetMapping({"/", "/index"})
  public String index(Model model) {
    // 查询当天新增话题
    model.addAttribute("topic_count", topicService.countToday());
    // 查询当天新增标签
    model.addAttribute("tag_count", tagService.countToday());
    // 查询当天新增评论
    model.addAttribute("comment_count", commentService.countToday());
    // 查询当天新增用户
    model.addAttribute("user_count", userService.countToday());

    // 获取操作系统的名字
    model.addAttribute("os_name", System.getProperty("os.name"));

    // 内存
    int kb = 1024;
    OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
        .getOperatingSystemMXBean();
    // 总的物理内存
    long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
    //已使用的物理内存
    long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
    // 获取系统cpu负载
    double systemCpuLoad = osmxb.getSystemCpuLoad();
    // 获取jvm线程负载
    double processCpuLoad = osmxb.getProcessCpuLoad();

    model.addAttribute("totalMemorySize", totalMemorySize);
    model.addAttribute("usedMemory", usedMemory);
    model.addAttribute("systemCpuLoad", systemCpuLoad);
    model.addAttribute("processCpuLoad", processCpuLoad);

    return "admin/index";
  }

}
