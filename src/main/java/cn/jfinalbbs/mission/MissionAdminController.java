package cn.jfinalbbs.mission;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by liuyang on 2015/6/26.
 */
@Before(AdminUserInterceptor.class)
public class MissionAdminController extends BaseController {

    public void index() {
        setAttr("page", Mission.me.paginate(getParaToInt("p", 1), 20));
        render("index.html");
    }
}
