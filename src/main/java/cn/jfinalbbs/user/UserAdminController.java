package cn.jfinalbbs.user;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by liuyang on 15/4/11.
 */
@Before(AdminUserInterceptor.class)
public class UserAdminController extends BaseController {

    public void index() {
        setAttr("page", User.me.page(getParaToInt("p", 1), 20));
        render("index.html");
    }

}
