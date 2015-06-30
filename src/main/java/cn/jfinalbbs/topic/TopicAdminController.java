package cn.jfinalbbs.topic;

import cn.jfinalbbs.collect.Collect;
import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import cn.jfinalbbs.reply.Reply;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * Created by liuyang on 15/4/9.
 */
@Before(AdminUserInterceptor.class)
public class TopicAdminController extends BaseController {

    public void index() {
        setAttr("page", Topic.me.page(getParaToInt("p", 1), 20));
        render("index.html");
    }

    @Before(Tx.class)
    public void delete() {
        String id = getPara("id");
        try {
            Topic topic = Topic.me.findById(id);
            String author_id = topic.get("author_id");
            Topic.me.deleteById(id);
            //删除回复
            Reply.me.deleteByTid(id);
            //删除收藏
            Collect.me.deleteByTid(id);
            //扣除积分
            User user = User.me.findById(author_id);
            if(user.getInt("score") <= 5) {
                user.set("score", 0).update();
            } else {
                user.set("score", user.getInt("score") - 5).update();
            }
            success();
        } catch (Exception e) {
            e.printStackTrace();
            error(Constants.DELETE_FAILURE);
        }
    }

    public void top() {
        String id = getPara("id");
        if(StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if(topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("top", topic.getInt("top") == 1?0:1).update();
                success(topic);
            }
        }
    }

    public void good() {
        String id = getPara("id");
        if(StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if(topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("good", topic.getInt("good") == 1?0:1).update();
                success(topic);
            }
        }
    }

    public void show_status() {
        String id = getPara("id");
        if(StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if(topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("show_status", topic.getInt("show_status") == 1?0:1).update();
                success(topic);
            }
        }
    }
}
