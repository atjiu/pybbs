package cn.jfinalbbs.interceptor;

import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.link.Link;
import cn.jfinalbbs.section.Section;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.DateUtil;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import java.util.Date;

/**
 * Created by liuyang on 15/4/2.
 */
public class CommonInterceptor implements Interceptor {

    public void intercept(Invocation ai) {
        // session cookie 互换
        String user_cookie = ai.getController().getCookie(Constants.USER_COOKIE);
        User user_session = (User) ai.getController().getSession().getAttribute(Constants.USER_SESSION);
        if(StrUtil.isBlank(user_cookie) && user_session != null) {
            ai.getController().setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user_session.getStr("token")), 30*24 * 60 * 60);
        } else if (!StrUtil.isBlank(user_cookie) && user_session == null) {
            User user = User.me.findByToken(StrUtil.getDecryptToken(user_cookie));
            ai.getController().setSessionAttr(Constants.USER_SESSION, user);
        }

        // 获取今天时间，放到session里
        ai.getController().setSessionAttr(Constants.TODAY, DateUtil.formatDate(new Date()));

        // 查询模块
        ai.getController().getRequest().setAttribute("sections", Section.me.findShow());

        // 查询友链
        ai.getController().getRequest().setAttribute("links", Link.me.findAll());

        ai.getController().getRequest().setAttribute("baseUrl", Constants.getBaseUrl());

        ai.invoke();
    }
}
