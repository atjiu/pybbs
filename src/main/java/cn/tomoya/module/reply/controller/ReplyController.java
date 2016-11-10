package cn.tomoya.module.reply.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.notification.entity.NotificationEnum;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 保存回复
     *
     * @param topicId
     * @param content
     * @return
     */
    @PostMapping("/save")
    public String save(Integer topicId, String content, HttpServletResponse response) {
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

                replyService.save(reply);

                //回复+1
                topic.setReplyCount(topic.getReplyCount() + 1);
                topicService.save(topic);

                //给话题作者发送通知
                if (user.getId() != topic.getUser().getId()) {
                    notificationService.sendNotification(getUser(), topic.getUser(), NotificationEnum.REPLY.name(), topic, content);
                }
                //给At用户发送通知
                List<String> atUsers = BaseEntity.fetchUsers(content);
                for (String u : atUsers) {
                    if (!u.equals(topic.getUser().getUsername())) {
                        User _user = userService.findByUsername(u);
                        if (_user != null) {
                            notificationService.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content);
                        }
                    }
                }
                return redirect(response, "/topic/" + topicId);
            }
        }
        return redirect(response, "/");
    }

    /**
     * 编辑回复
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        Reply reply = replyService.findById(id);
        model.addAttribute("reply", reply);
        return render("/reply/edit");
    }

    /**
     * 更新回复内容
     *
     * @param id
     * @param topicId
     * @param content
     * @param response
     * @return
     */
    @GetMapping("/update")
    public String update(Integer id, Integer topicId, String content, HttpServletResponse response) {
        Reply reply = replyService.findById(id);
        if (reply == null) {
            renderText(response, "回复不存在");
            return null;
        } else {
            reply.setContent(content);
            replyService.save(reply);
            return redirect(response, "/topic/" + topicId);
        }
    }

    /**
     * 删除回复
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
}
