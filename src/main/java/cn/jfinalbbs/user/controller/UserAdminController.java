package cn.jfinalbbs.user.controller;

import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import cn.jfinalbbs.user.model.User;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by liuyang on 15/4/11.
 */
@Before(AdminUserInterceptor.class)
public class UserAdminController extends Controller {

    public void index() {
        setAttr("page", User.me.page(getParaToInt("p", 1), 20));
        render("index.html");
    }

}
