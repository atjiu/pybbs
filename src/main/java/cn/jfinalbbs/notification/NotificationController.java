package cn.jfinalbbs.notification;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.UserInterceptor;
import cn.jfinalbbs.user.User;
import com.jfinal.aop.Before;

/**
 * Created by liuyang on 15/4/7.
 */
public class NotificationController extends BaseController {

    @Before(UserInterceptor.class)
    public void countnotread() {
        User user = getSessionAttr(Constants.USER_SESSION);
        if(user == null) {
            error(Constants.ResultDesc.FAILURE);
        } else {
            try {
                int count = Notification.me.countNotRead(user.getStr("id"));
                success(count);
            } catch (Exception e) {
                e.printStackTrace();
                error(Constants.ResultDesc.FAILURE);
            }
        }
    }
}
