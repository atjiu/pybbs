package cn.jfinalbbs.reply;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
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
            getModel(Reply.class).deleteById(id);
            Reply.me.deleteQuoteByRid(id);
            success();
        } catch (Exception e) {
            e.printStackTrace();
            error(Constants.DELETE_FAILURE);
        }
    }
}
