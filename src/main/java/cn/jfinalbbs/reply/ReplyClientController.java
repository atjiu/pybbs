package cn.jfinalbbs.reply;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.notification.Notification;
import cn.jfinalbbs.topic.Topic;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.DateUtil;
import cn.jfinalbbs.utils.StrUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyang on 2015/6/27.
 */
public class ReplyClientController extends BaseController {

    public void create() {
        error("暂不支持回复");
    }
}
