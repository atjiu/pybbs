package cn.jfinalbbs.index.controller;

import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by liuyang on 15/4/9.
 */
@Before(AdminUserInterceptor.class)
public class IndexAdminController extends Controller {

    public void index() {
        render("index.html");
    }

    public void logout() {
        removeSessionAttr(Constants.ADMIN_USER_SESSION);
        redirect("/");
    }
}