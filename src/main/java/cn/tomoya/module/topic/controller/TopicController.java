package cn.tomoya.module.topic.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private SiteConfig siteConfig;

    /**
     * 创建话题
     *
     * @return
     */
    @GetMapping("/create")
    public String create() {
        return render("/topic/create");
    }

    /**
     * 保存话题
     *
     * @param title
     * @param content
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(String tab, String title, String content, Model model, HttpServletResponse response) {
        String errors;
        if (StringUtils.isEmpty(title)) {
            errors = "标题不能为空";
        }  else if (!siteConfig.getSections().contains(tab)) {
            errors = "版块不存在";
        } else {
            User user = getUser();
            Topic topic = new Topic();
            topic.setTab(tab);
            topic.setTitle(title);
            topic.setContent(content);
            topic.setInTime(new Date());
            topic.setUp(0);
            topic.setView(0);
            topic.setUser(user);
            topic.setGood(false);
            topic.setTop(false);
            topicService.save(topic);
            return redirect(response, "/topic/" + topic.getId());
        }
        model.addAttribute("errors", errors);
        return render("/topic/create");
    }

    /**
     * 编辑话题
     *
     * @param id
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable int id, HttpServletResponse response, Model model) {
        Topic topic = topicService.findById(id);
        if (topic == null) {
            renderText(response, "话题不存在");
            return null;
        } else {
            model.addAttribute("topic", topic);
            return render("/topic/edit");
        }
    }

    /**
     * 更新话题
     *
     * @param title
     * @param content
     * @return
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, String tab, String title, String content, HttpServletResponse response) {
        Topic topic = topicService.findById(id);
        User user = getUser();
        if (topic.getUser().getId() == user.getId()) {
            if(!siteConfig.getSections().contains(tab)) throw new IllegalArgumentException("版块不存在");
            topic.setTab(tab);
            topic.setTitle(title);
            topic.setContent(content);
            topicService.save(topic);
            return redirect(response, "/topic/" + topic.getId());
        } else {
            renderText(response, "非法操作");
            return null;
        }
    }

    /**
     * 话题详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, HttpServletResponse response, Model model) {
        if (id != null) {
            Topic topic = topicService.findById(id);
            //浏览量+1
            topic.setView(topic.getView() + 1);
            topicService.save(topic);//更新话题数据
            List<Reply> replies = replyService.findByTopic(topic);
            model.addAttribute("topic", topic);
            model.addAttribute("replies", replies);
            model.addAttribute("user", getUser());
            model.addAttribute("author", topic.getUser());
            model.addAttribute("otherTopics", topicService.findByUser(1, 7, topic.getUser()));
            model.addAttribute("collect", collectService.findByUserAndTopic(getUser(), topic));
            model.addAttribute("collectCount", collectService.countByTopic(topic));
            return render("/topic/detail");
        } else {
            renderText(response, "话题不存在");
            return null;
        }
    }

    /**
     * 删除话题
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, HttpServletResponse response) {
        topicService.deleteById(id);
        return redirect(response, "/");
    }

    /**
     * 加/减精华
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}/good")
    public String good(@PathVariable Integer id, HttpServletResponse response) {
        Topic topic = topicService.findById(id);
        topic.setGood(!topic.isGood());
        topicService.save(topic);
        return redirect(response, "/topic/" + id);
    }

    /**
     * 置/不置顶
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}/top")
    public String top(@PathVariable Integer id, HttpServletResponse response) {
        Topic topic = topicService.findById(id);
        topic.setTop(!topic.isTop());
        topicService.save(topic);
        return redirect(response, "/topic/" + id);
    }
}
