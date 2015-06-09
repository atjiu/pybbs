package cn.jfinalbbs.index;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by liuyang on 15/4/9.
 */
@Before(AdminUserInterceptor.class)
public class IndexAdminController extends BaseController {

    public void index() {
        render("index.html");
    }

    public void logout() {
//        removeSessionAttr(Constants.ADMIN_USER_SESSION);
        removeCookie(Constants.COOKIE_ADMIN_TOKEN);
        redirect(Constants.getBaseUrl() + "/");
    }
}