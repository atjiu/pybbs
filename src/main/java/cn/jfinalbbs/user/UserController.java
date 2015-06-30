package cn.jfinalbbs.user;

import cn.jfinalbbs.collect.Collect;
import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.UserInterceptor;
import cn.jfinalbbs.notification.Notification;
import cn.jfinalbbs.topic.Topic;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by liuyang on 15/4/3.
 */
public class UserController extends BaseController {

    public void index() {
        String id = getPara(0);
        User user = User.me.findById(id);
        if(user != null) {
            setAttr("current_user", user);
            Page<Collect> collectPage = Collect.me.findByAuthorId(getParaToInt("p", 1), 20, user.getStr("id"));
            setAttr("collectPage", collectPage);
            List<Topic> topics = Topic.me.findFiveByAuthorId(user.getStr("id"));
            setAttr("topics", topics);
            render("front/user/index.html");
        } else {
            renderText(Constants.OP_ERROR_MESSAGE);
        }
    }

    public void collects() {
        String uid = getPara(0);
        User user = User.me.findById(uid);
        if(user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("current_user", user);
            Page<Collect> collectPage = Collect.me.findByAuthorIdWithTopic(getParaToInt("p", 1), 20, user.getStr("id"));
            setAttr("page", collectPage);
            render("front/user/collects.html");
        }
    }

    public void topics() {
        String uid = getPara(0);
        User user = User.me.findById(uid);
        if(user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("current_user", user);
            Page<Topic> page = Topic.me.paginateByAuthorId(getParaToInt("p", 1), 20, user.getStr("id"));
            setAttr("page", page);
            render("front/user/topics.html");
        }
    }

    public void top100() {
        List<User> top100 = User.me.findBySize(100);
        setAttr("top100", top100);
        render("front/user/top100.html");
    }

    @Before(UserInterceptor.class)
    public void message() {
        String uid = getPara(0);
        if(StrUtil.isBlank(uid)) renderText(Constants.OP_ERROR_MESSAGE);
        List<Notification> notifications = Notification.me.findNotReadByAuthorId(uid);
        setAttr("notifications", notifications);
        Page<Notification> oldMessages = Notification.me.paginate(getParaToInt("p", 1), 20, uid);
        setAttr("oldMessages", oldMessages);
        //将消息置为已读
        Notification.me.updateNotification(uid);
        render("front/user/message.html");
    }

    @Before(UserInterceptor.class)
    public void setting() throws InterruptedException {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            setAttr("save", getPara("save"));
            render("front/user/setting.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            User user = getSessionAttr(Constants.USER_SESSION);
            try {
                String url = getPara("url");
                if(!StrUtil.isBlank(url)) {
                    if (!url.substring(0, 7).equalsIgnoreCase("http://")) {
                        url = "http://" + url;
                    }
                }
                user.set("url", url)
                    .set("signature", getPara("signature")).update();
                //保存成功
                setSessionAttr(Constants.USER_SESSION, user);
                redirect(Constants.getBaseUrl() + "/user/setting?save=success");
            } catch (Exception e) {
                e.printStackTrace();
                //保存失败
                redirect(Constants.getBaseUrl() + "/user/setting?save=fail");
            }
        }
    }
}
