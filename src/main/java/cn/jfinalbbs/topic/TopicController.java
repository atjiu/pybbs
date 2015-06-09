package cn.jfinalbbs.topic;

import cn.jfinalbbs.collect.Collect;
import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.UserInterceptor;
import cn.jfinalbbs.reply.Reply;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.Date;
import java.util.List;

/**
 * Created by liuyang on 15/4/2.
 */
public class TopicController extends BaseController {

    public void index() {
        String id = getPara(0);
        Topic topic = Topic.me.findByIdWithUser(id);
        if (topic != null) {
            List<Reply> replies = Reply.me.findByTid(id);
            setAttr("topic", topic);
            setAttr("replies", replies);
            //查询收藏信息
            User user = getSessionAttr(Constants.USER_SESSION);
            if(user != null) {
                Collect collect = Collect.me.findByTidAndAuthorId(id, user.getStr("id"));
                setAttr("collect", collect);
            }
            render("index.html");
        } else {
            renderText("您查询的话题不存在");
        }
    }

    @Before(UserInterceptor.class)
    public void create(){
        render("create.html");
    }

    @Before(UserInterceptor.class)
    public void edit(){
        String tid = getPara(0);
        Topic topic = Topic.me.findWithSection(tid);
        if(topic == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("topic", topic);
            render("edit.html");
        }
    }

    @Before(UserInterceptor.class)
    public void update() {
        String tid = getPara("tid");
        Topic topic = Topic.me.findById(tid);
        if(topic == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            String sid = getPara("sid");
            String title = getPara("title");
            String content = getPara("content");
            String original_url = getPara("original_url");
            topic.set("title", title)
                    .set("s_id", sid)
                    .set("content", content)
                    .set("reposted", StrUtil.isBlank(original_url) ? 0:1)
                    .set("original_url", original_url)
                    .set("modify_time", new Date())
                    .update();
            redirect(Constants.getBaseUrl() + "/topic/" + tid);
        }
    }

    @Before({UserInterceptor.class, Tx.class})
    public void delete() {
        String tid = getPara(0);
        Topic topic = Topic.me.findById(tid);
        User user = getSessionAttr(Constants.USER_SESSION);
        if(topic == null || !topic.get("author_id").equals(user.get("id"))) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            try {
                topic.delete();
                //删除回复
                Reply.me.deleteByTid(topic.getStr("id"));
                //删除收藏
                Collect.me.deleteByTid(topic.getStr("id"));
                redirect(Constants.getBaseUrl() + "/");
            } catch (Exception e) {
                e.printStackTrace();
                renderText("删除失败");
            }
        }
    }

    @Before({UserInterceptor.class, Tx.class})
    public void save() {
        User user = getSessionAttr(Constants.USER_SESSION);
        String sid = getPara("sid");
        String title = getPara("title");
        String content = getPara("content");
        String original_url = getPara("original_url");
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
                .save();
        //将积分增加3分
        user.set("score", user.getInt("score") + 3).update();
        redirect(Constants.getBaseUrl() + "/topic/" + topic.get("id"));
    }

}
