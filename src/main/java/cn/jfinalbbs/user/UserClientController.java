package cn.jfinalbbs.user;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.utils.StrUtil;

/**
 * Created by liuyang on 2015/6/27.
 */
public class UserClientController extends BaseController {

    public void index() {
        String token = getPara("token");
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            if (StrUtil.isBlank(token)) {
                error(Constants.ResultDesc.FAILURE);
            } else {
                User user = User.me.findByToken(token);
                if (user == null) {
                    error(Constants.ResultDesc.FAILURE);
                } else {
                    success(user);
                }
            }
        } else {
            error("请使用post请求");
        }
    }
}
