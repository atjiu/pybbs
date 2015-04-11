package cn.jfinalbbs.topic.controller;

import cn.jfinalbbs.collect.model.Collect;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import cn.jfinalbbs.reply.model.Reply;
import cn.jfinalbbs.topic.model.Topic;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * Created by liuyang on 15/4/9.
 */
@Before(AdminUserInterceptor.class)
public class TopicAdminController extends Controller {

    public void index() {
        setAttr("page", Topic.me.page(getParaToInt("p", 1), 20));
        render("index.html");
    }

    @Before(Tx.class)
    public void delete() {
        String id = getPara(0);
        Topic.me.deleteById(id);
        //删除回复
        Reply.me.deleteByTid(id);
        //删除收藏
        Collect.me.deleteByTid(id);
        redirect("/admin/topic");
    }
}
