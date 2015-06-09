package cn.jfinalbbs.mission;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.UserInterceptor;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.DateUtil;
import com.jfinal.aop.Before;

import java.util.Date;
import java.util.Random;

/**
 * Created by liuyang on 15/4/2.
 */
public class MissionController extends BaseController {

    // 每日登录后，点击领取积分，可用于发帖，回复等
    @Before(UserInterceptor.class)
    public void daily() {
        User user = getSessionAttr(Constants.USER_SESSION);
        // 查询今天是否获取过奖励
        Mission mission = Mission.me.findByInTime(user.getStr("id"),
                DateUtil.formatDate(new Date()) + " 00:00:00",
                DateUtil.formatDate(new Date()) + " 23:59:59");
        if(mission == null) {
            Random random = new Random();
            int score = random.nextInt(10) + 1;// 随机积分，1-10分
            user.set("score", user.getInt("score") + score).set("mission", DateUtil.formatDate(new Date())).update();
            getModel(Mission.class)
                    .set("score", score)
                    .set("author_id", user.get("id"))
                    .set("in_time", new Date()).save();
            setAttr("score", score);
        } else {
            setAttr("msg", "您今天已经领取了奖励，明天在来吧");
        }
        render("result.html");
    }
}
