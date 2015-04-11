package cn.jfinalbbs.notification.controller;

import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.UserInterceptor;
import cn.jfinalbbs.notification.model.Notification;
import cn.jfinalbbs.user.model.User;
import cn.jfinalbbs.utils.Result;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by liuyang on 15/4/7.
 */
public class NotificationController extends Controller {

    @Before(UserInterceptor.class)
    public void countnotread() {
        User user = getSessionAttr(Constants.USER_SESSION);
        Result result = new Result(Constants.ResultCode.SUCCESS, Constants.ResultDesc.SUCCESS, null);
        if(user == null) {
            result.setCode(Constants.ResultCode.FAILURE);
            result.setDescription(Constants.ResultDesc.FAILURE);
        } else {
            try {
                int count = Notification.me.countNotRead(user.getStr("id"));
                result.setDetail(count);
            } catch (Exception e) {
                e.printStackTrace();
                result.setCode(Constants.ResultCode.FAILURE);
                result.setDescription(Constants.ResultDesc.FAILURE);
            }
        }
        renderJson(result);
    }
}
