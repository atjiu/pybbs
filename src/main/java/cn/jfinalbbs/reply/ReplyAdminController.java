package cn.jfinalbbs.reply;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import cn.jfinalbbs.user.User;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * Created by liuyang on 15/4/11.
 */
@Before(AdminUserInterceptor.class)
public class ReplyAdminController extends BaseController {

    public void index() {
        setAttr("page", Reply.me.page(getParaToInt("p", 1), 20));
        render("index.html");
    }

    @Before(Tx.class)
    public void delete() {
        String id = getPara("id");
        try {
            Reply reply = Reply.me.findById(id);
            User user = User.me.findById(reply.get("author_id"));
            getModel(Reply.class).deleteById(id);
            if(user.getInt("score") <= 2) {
                user.set("score", 0).update();
            } else {
                user.set("score", user.getInt("score") - 2).update();
            }
            Reply.me.deleteQuoteByRid(id);
            success();
        } catch (Exception e) {
            e.printStackTrace();
            error(Constants.DELETE_FAILURE);
        }
    }
}
