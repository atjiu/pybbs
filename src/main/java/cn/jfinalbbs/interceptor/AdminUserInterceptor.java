package cn.jfinalbbs.interceptor;

import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.user.model.AdminUser;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

import java.io.UnsupportedEncodingException;

/**
 * Created by liuyang on 15/4/9.
 */
public class AdminUserInterceptor implements Interceptor {
    @Override
    public void intercept(ActionInvocation ai) {
        AdminUser adminUser = ai.getController().getSessionAttr(Constants.ADMIN_USER_SESSION);
        if(adminUser == null) {
            ai.getController().redirect("/adminlogin");
        } else {
            ai.invoke();
        }
    }
}
