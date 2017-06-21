package cn.tomoya.module.label.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.exception.ApiException;
import cn.tomoya.exception.Result;
import cn.tomoya.module.label.entity.Label;
import cn.tomoya.module.label.service.LabelService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.util.FileUploadEnum;
import cn.tomoya.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by tomoya on 2017/6/16.
 */
@Controller
@RequestMapping("/admin/label")
public class LabelAdminController extends BaseController {

  @Autowired
  private LabelService labelService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private FileUtil fileUtil;

  /**
   * label's topics
   *
   * @param name
   * @param model
   * @return
   */
  @GetMapping("/{name}")
  public String topic(@PathVariable String name, Integer p, Model model) {
    Label label = labelService.findByName(name);
    model.addAttribute("p", p);
    model.addAttribute("label", label);
    return render("/admin/label/topic");
  }

  /**
   * label list page
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Integer p, Model model) {
    model.addAttribute("p", p);
    return render("/admin/label/list");
  }

  /**
   * edit label page
   *
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Integer id, Model model) {
    Label label = labelService.findById(id);
    model.addAttribute("label", label);
    return render("/admin/label/edit");
  }

  /**
   * update label
   *
   * @param request
   * @param id
   * @param name
   * @param intro
   * @param file
   * @return
   * @throws Exception
   */
  @PostMapping("/update")
  public String update(Integer id, String name, String intro, @RequestParam("file") MultipartFile file,
                       HttpServletResponse response) throws Exception {
    Label label = labelService.findById(id);
    Label _label = labelService.findByName(name);

    if (label.getId() != _label.getId()) throw new Exception("要修改的标签名已经存在");

    // query topic by labelId
    List<Topic> topics = topicService.fuzzyTopicByLabel(id);

    label.setName(name);
    label.setIntro(intro);
    label.setModifyTime(new Date());
    label.setTopicCount(topics.size());
    label.setImage(fileUtil.uploadFile(file, FileUploadEnum.LABEL, String.valueOf(label.getId())));
    labelService.save(label);
    return redirect(response, "/admin/label/list");
  }

  /**
   * delete label
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/delete")
  @ResponseBody
  public Result delete(@PathVariable Integer id) throws ApiException {
    Label label = labelService.findById(id);

    if (label.getTopicCount() > 0)
      throw new ApiException("不能删除这个标签，还有话题关联了这个标签，要先处理关联了这个标签的话题");

    labelService.delete(label);
    return Result.success();
  }

}
