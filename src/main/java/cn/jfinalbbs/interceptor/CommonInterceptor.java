package cn.jfinalbbs.interceptor;

import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.user.model.User;
import cn.jfinalbbs.utils.DateUtil;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

import java.util.Date;

/**
 * Created by liuyang on 15/4/2.
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public void intercept(ActionInvocation ai) {
        String user_cookie = ai.getController().getCookie(Constants.USER_COOKIE);
        User user_session = (User) ai.getController().getSession().getAttribute(Constants.USER_COOKIE);
        if(StrUtil.isBlank(user_cookie) && user_session != null) {
            ai.getController().setCookie(Constants.USER_COOKIE, user_session.getStr("id"), 30*24 * 60 * 60);
        } else if (!StrUtil.isBlank(user_cookie) && user_session == null) {
            User user = User.me.findById(user_cookie);
            ai.getController().setSessionAttr(Constants.USER_SESSION, user);
        }
        // 获取今天时间，放到session里
        ai.getController().setSessionAttr(Constants.TODAY, DateUtil.formatDate(new Date()));
        ai.invoke();
    }
}
