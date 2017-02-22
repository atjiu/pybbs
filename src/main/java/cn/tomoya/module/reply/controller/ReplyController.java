package cn.tomoya.module.reply.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.BaseEntity;
import cn.tomoya.exception.ApiException;
import cn.tomoya.exception.Result;
import cn.tomoya.module.notification.entity.NotificationEnum;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/reply")
public class ReplyController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * save comment
     *
     * @param topicId
     * @param content
     * @return
     */
    @PostMapping("/save")
    public String save(Integer topicId, String content, HttpServletResponse response) {
        if(getUser().isBlock()) return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.accountDisabled"));
        if (topicId != null) {
            Topic topic = topicService.findById(topicId);
            if (topic != null) {
                User user = getUser();
                Reply reply = new Reply();
                reply.setUser(user);
                reply.setTopic(topic);
                reply.setInTime(new Date());
                reply.setUp(0);
                reply.setContent(content);
                reply.setEditor(settingService.getEditor());

                replyService.save(reply);

                //回复+1
                topic.setReplyCount(topic.getReplyCount() + 1);
                topicService.save(topic);

                //给话题作者发送通知
                if (user.getId() != topic.getUser().getId()) {
                    notificationService.sendNotification(getUser(), topic.getUser(), NotificationEnum.REPLY.name(), topic, content, reply.getEditor());
                }
                //给At用户发送通知
                String pattern = null;
                if(settingService.getEditor().equals("wangeditor")) pattern = "\">[^\\s]+</a>?";
                List<String> atUsers = BaseEntity.fetchUsers(pattern, content);
                for (String u : atUsers) {
                    if(settingService.getEditor().equals("markdown")) {
                        u = u.replace("@", "").trim();
                    } else if(settingService.getEditor().equals("wangeditor")) {
                        u = u.replace("\">@", "").replace("</a>", "").trim();
                    }
                    if (!u.equals(user.getUsername())) {
                        User _user = userService.findByUsername(u);
                        if (_user != null) {
                            notificationService.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content, reply.getEditor());
                        }
                    }
                }
                return redirect(response, "/topic/" + topicId);
            }
        }
        return redirect(response, "/");
    }

    /**
     * edit comment
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model, HttpServletResponse response) {
        if(getUser().isBlock()) return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.accountDisabled"));
        Reply reply = replyService.findById(id);
        model.addAttribute("reply", reply);
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.reply.edit"));
        return render("/reply/edit");
    }

    /**
     * update comment
     *
     * @param id
     * @param topicId
     * @param content
     * @param response
     * @return
     */
    @PostMapping("/update")
    public String update(Integer id, Integer topicId, String content, HttpServletResponse response) {
        if(getUser().isBlock()) return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.accountDisabled"));
        Reply reply = replyService.findById(id);
        if (reply == null) {
            return renderText(response, localeMessageSourceUtil.getMessage("site.prompt.text.commentNotExist"));
        } else {
            reply.setContent(content);
            replyService.save(reply);
            return redirect(response, "/topic/" + topicId);
        }
    }

    /**
     * delete comment
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, HttpServletResponse response) {
        if (id != null) {
            Map map = replyService.delete(id, getUser());
            return redirect(response, "/topic/" + map.get("topicId"));
        }
        return redirect(response, "/");
    }

    /**
     * up vote
     * @param id
     * @return
     * @throws ApiException
     */
    @GetMapping("/{id}/up")
    @ResponseBody
    public Result up(@PathVariable Integer id) throws ApiException {
        User user = getUser();
        Reply reply = replyService.up(user.getId(), id);
        if(reply == null) throw new ApiException(localeMessageSourceUtil.getMessage("site.prompt.text.commentNotExist"));
        return Result.success(reply.getUpDown());
    }

    /**
     * cancel up vote
     * @param id
     * @return
     * @throws ApiException
     */
    @GetMapping("/{id}/cancelUp")
    @ResponseBody
    public Result cancelUp(@PathVariable Integer id) throws ApiException {
        User user = getUser();
        Reply reply = replyService.cancelUp(user.getId(), id);
        if(reply == null) throw new ApiException(localeMessageSourceUtil.getMessage("site.prompt.text.commentNotExist"));
        return Result.success(reply.getUpDown());
    }

    /**
     * down vote
     * @param id
     * @return
     * @throws ApiException
     */
    @GetMapping("/{id}/down")
    @ResponseBody
    public Result down(@PathVariable Integer id) throws ApiException {
        User user = getUser();
        Reply reply = replyService.down(user.getId(), id);
        if(reply == null) throw new ApiException(localeMessageSourceUtil.getMessage("site.prompt.text.commentNotExist"));
        return Result.success(reply.getUpDown());
    }

    /**
     * cancel down vote
     * @param id
     * @return
     * @throws ApiException
     */
    @GetMapping("/{id}/cancelDown")
    @ResponseBody
    public Result cancelDown(@PathVariable Integer id) throws ApiException {
        User user = getUser();
        Reply reply = replyService.cancelDown(user.getId(), id);
        if(reply == null) throw new ApiException(localeMessageSourceUtil.getMessage("site.prompt.text.commentNotExist"));
        return Result.success(reply.getUpDown());
    }
}
