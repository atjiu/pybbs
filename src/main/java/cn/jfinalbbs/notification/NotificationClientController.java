package cn.jfinalbbs.notification;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.plugin.activerecord.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyang on 2015/6/27.
 */
public class NotificationClientController extends BaseController {

    public void countnotread() {
        String token = getPara("token");
        if(StrUtil.isBlank(token)) {
            error("请先登录");
        } else {
            //根据token获取用户信息
            User user = User.me.findByToken(token);
            if (user == null) {
                error("用户不存在，请退出重新登录");
            } else {
                int count = Notification.me.countNotRead(user.getStr("id"));
                success(count);
            }
        }
    }

    public void index() {
        String token = getPara("token");
        if(StrUtil.isBlank(token)) {
            error("请先登录");
        } else {
            //根据token获取用户信息
            User user = User.me.findByToken(token);
            if(user == null) {
                error("用户不存在，请退出重新登录");
            } else {
                List<Notification> notifications = Notification.me.findNotReadByAuthorId(user.getStr("id"));
                Page<Notification> oldMessages = Notification.me.paginate(getParaToInt("p", 1), getParaToInt("size", 20), user.getStr("id"));
                //将消息置为已读
                Notification.me.updateNotification(user.getStr("id"));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("notifications", notifications);
                map.put("oldMessages", oldMessages);
                success(map);
            }
        }
    }
}
