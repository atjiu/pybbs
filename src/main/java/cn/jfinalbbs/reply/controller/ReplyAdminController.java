package cn.jfinalbbs.reply.controller;

import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import cn.jfinalbbs.reply.model.Reply;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.io.UnsupportedEncodingException;

/**
 * Created by liuyang on 15/4/11.
 */
@Before(AdminUserInterceptor.class)
public class ReplyAdminController extends Controller {

    public void index() {
        setAttr("page", Reply.me.page(getParaToInt("p", 1), 20));
        render("index.html");
    }

    @Before(Tx.class)
    public void delete() {
        String id = getPara(0);
        getModel(Reply.class).deleteById(id);
        Reply.me.deleteQuoteByRid(id);
        redirect("/admin/reply/index");
    }
}
