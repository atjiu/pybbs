package cn.jfinalbbs.topic;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.reply.Reply;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.StrUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomoya on 15/6/9.
 */
public class TopicClientController extends BaseController {

    public void index() {
        Map<String, Object> map = new HashMap<String, Object>();
        String id = getPara(0);
        if(StrUtil.isBlank(id)) {
            error("非法请求");
        } else {
            Topic topic = Topic.me.findByIdWithUser(id);
            List<Reply> replies = Reply.me.findByTid(id);
            map.put("topic", topic);
            map.put("replies", replies);
            success(map);
        }
    }

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
                String sid = getPara("sid");
                String title = getPara("title");
                String content = getPara("content");
                String original_url = getPara("original_url");
                if(StrUtil.isBlank(sid) || StrUtil.isBlank(title) || StrUtil.isBlank(content)) {
                    error(Constants.OP_ERROR_MESSAGE);
                } else {
                    Topic topic = new Topic();
                    topic.set("id", StrUtil.getUUID())
                            .set("in_time", new Date())
                            .set("s_id", sid)
                            .set("title", title)
                            .set("content", content)
                            .set("view", 0)
                            .set("author_id", user.get("id"))
                            .set("reposted", StrUtil.isBlank(original_url) ? 0 : 1)
                            .set("original_url", original_url)
                            .set("top", 0)
                            .set("good", 0)
                            .set("show_status", 1)
                            .save();
                    //将积分增加3分
                    user.set("score", user.getInt("score") + 3).update();
                    success(topic);
                }
            }
        }
    }
}
