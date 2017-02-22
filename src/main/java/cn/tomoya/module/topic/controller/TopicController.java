package cn.tomoya.module.topic.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.section.service.SectionService;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.LocaleMessageSourceUtil;
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
    private SectionService sectionService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * create topic
     *
     * @return
     */
    @GetMapping("/create")
    public String create(HttpServletResponse response, Model model) {
        if(getUser().isBlock()) {
            return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.accountDisabled"));
        }
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.topic.create"));
        return render("/topic/create");
    }

    /**
     * save topic
     *
     * @param title
     * @param content
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(String tab, String title, String content, Model model, HttpServletResponse response) {
        if(getUser().isBlock()) {
            return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.accountDisabled"));
        }
        String errors;
        if (StringUtils.isEmpty(title)) {
            errors = localeMessageSourceUtil.getMessage("site.prompt.text.topicTitleCantEmpty");
        }  else if (sectionService.findByName(tab) != null) {
            errors = localeMessageSourceUtil.getMessage("site.prompt.text.tabNotExist");;
        } else {
            User user = getUser();
            Topic topic = new Topic();
            topic.setTab(tab);
            topic.setTitle(title);
            topic.setContent(content);
            topic.setInTime(new Date());
            topic.setView(0);
            topic.setUser(user);
            topic.setGood(false);
            topic.setTop(false);
            topic.setEditor(settingService.getEditor());
            topic.setLock(false);
            topicService.save(topic);
            return redirect(response, "/topic/" + topic.getId());
        }
        model.addAttribute("errors", errors);
        return render("/topic/create");
    }

    /**
     * edit topic
     *
     * @param id
     * @param response
     * @param model
     * @return
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, HttpServletResponse response, Model model) {
        Topic topic = topicService.findById(id);
        if (topic == null) {
            return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.topicNotExist"));
        } else {
            model.addAttribute("topic", topic);
            model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.topic.edit"));
            return render("/topic/edit");
        }
    }

    /**
     * update topic
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
            if(sectionService.findByName(tab) != null) throw new IllegalArgumentException(localeMessageSourceUtil.getMessage("site.prompt.text.tabNotExist"));
            topic.setTab(tab);
            topic.setTitle(title);
            topic.setContent(content);
            topicService.save(topic);
            return redirect(response, "/topic/" + topic.getId());
        } else {
            return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.illegalOperation"));
        }
    }

    /**
     * topic detail
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, HttpServletResponse response, Model model) {
        if (id != null) {
            Topic topic = topicService.findById(id);
            //view+1
            topic.setView(topic.getView() + 1);
            topicService.save(topic);
            List<Reply> replies = replyService.findByTopic(topic);
            model.addAttribute("topic", topic);
            model.addAttribute("replies", replies);
            model.addAttribute("user", getUser());
            model.addAttribute("author", topic.getUser());
            model.addAttribute("otherTopics", topicService.findByUser(1, 7, topic.getUser()));
            model.addAttribute("collect", collectService.findByUserAndTopic(getUser(), topic));
            model.addAttribute("collectCount", collectService.countByTopic(topic));
            model.addAttribute("pageTitle", topic.getTitle());
            return render("/topic/detail");
        } else {
            return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.topicNotExist"));
        }
    }

    /**
     * delete topic
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
     * set good or cancel good
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
     * set top or cancel top
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

    /**
     * set lock or cancel lock
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}/lock")
    public String lock(@PathVariable Integer id, HttpServletResponse response) {
        Topic topic = topicService.findById(id);
        topic.setLock(!topic.isLock());
        topicService.save(topic);
        return redirect(response, "/topic/" + id);
    }
}
