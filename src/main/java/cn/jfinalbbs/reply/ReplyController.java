package cn.jfinalbbs.reply;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.UserInterceptor;
import cn.jfinalbbs.notification.Notification;
import cn.jfinalbbs.topic.Topic;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Before;

import java.util.Date;

/**
 * Created by liuyang on 15/4/3.
 */
public class ReplyController extends BaseController {

    @Before(UserInterceptor.class)
    public void index() {
        String tid = getPara(0);
        Topic topic = Topic.me.findById(tid);
        User user = getSessionAttr(Constants.USER_SESSION);
        if (topic == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            // 增加1积分
            Reply reply = new Reply();
            String quote = getPara("quote");
            String content = getPara("content");
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
            redirect(Constants.getBaseUrl() + "/topic/" + tid + ".html" + "#" + reply.get("id"));
        }
    }
}
