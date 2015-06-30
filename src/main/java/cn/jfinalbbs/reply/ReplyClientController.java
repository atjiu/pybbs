package cn.jfinalbbs.reply;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.notification.Notification;
import cn.jfinalbbs.topic.Topic;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.StrUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyang on 2015/6/27.
 */
public class ReplyClientController extends BaseController {

    public void create() {
        String token = getPara("token");
        if(StrUtil.isBlank(token)) {
            error("请先登录");
        } else {
            //根据token获取用户信息
            User user = User.me.findByToken(token);
            if(user == null) {
                error("用户不存在，请退出重新登录");
            } else {
                String tid = getPara("tid");
                Topic topic = Topic.me.findById(tid);
                if (topic == null) {
                    error("回复的话题不存在");
                } else {
                    // 增加1积分
                    Reply reply = new Reply();
                    String quote = getPara("quote");
                    String content = getPara("content");
                    if(StrUtil.isBlank(content)) {
                        error("请输入回复内容");
                    } else {
                        reply.set("id", StrUtil.getUUID())
                                .set("tid", tid)
                                .set("content", content)
                                .set("in_time", new Date())
                                .set("quote", 0)
                                .set("author_id", user.get("id"));
                        Reply r = Reply.me.findById(quote);
                        if (r != null) {
                            User quote_user = User.me.findById(r.get("author_id"));
                            reply.set("quote", quote)
                                    .set("quote_content", r.get("content"))
                                    .set("quote_author_nickname", quote_user.get("nickname"));
                            // 通知话题发布者
                            Notification replyNoti = new Notification();
                            replyNoti.set("tid", tid)
                                    .set("rid", reply.get("id"))
                                    .set("read", 0)
                                    .set("message", Constants.NOTIFICATION_MESSAGE2)
                                    .set("from_author_id", user.get("id"))
                                    .set("author_id", r.get("author_id"))
                                    .set("in_time", new Date()).save();
                        }
                        reply.save();
                        user.set("score", user.getInt("score") + 1).update();
                        //引用回复且是自己的话题的话，不用发送通知
                        if (!user.get("id").equals(topic.get("author_id"))) {
                            // 通知话题发布者
                            Notification topicNoti = new Notification();
                            topicNoti.set("tid", tid)
                                    .set("rid", reply.get("id"))
                                    .set("read", 0)
                                    .set("message", Constants.NOTIFICATION_MESSAGE1)
                                    .set("from_author_id", user.get("id"))
                                    .set("author_id", topic.get("author_id"))
                                    .set("in_time", new Date()).save();
                        }
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("topic", topic);
                        map.put("reply", reply);
                        success(map);
                    }
                }
            }
        }
    }
}
