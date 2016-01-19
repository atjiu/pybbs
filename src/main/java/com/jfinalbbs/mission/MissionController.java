package com.jfinalbbs.mission;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.UserInterceptor;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.DateUtil;
import com.jfinal.aop.Before;

import java.util.Date;
import java.util.Random;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class MissionController extends BaseController {

    // 每日登录后，点击领取积分，可用于发帖，回复等
    @Before(UserInterceptor.class)
    public void daily() {
        User sessionUser = getSessionAttr(Constants.USER_SESSION);
        User user = User.me.findById(sessionUser.get("id"));
        // 查询今天是否获取过奖励
        Mission mission = Mission.me.findByInTime(user.getStr("id"),
                DateUtil.formatDate(new Date()) + " 00:00:00",
                DateUtil.formatDate(new Date()) + " 23:59:59");
        if(mission == null) {
            //查询最后一次签到记录
            Mission lastMission = Mission.me.findLastByAuthorId(user.getStr("id"));
            Integer day = 1;
            if(lastMission != null) {
                String lastMissionInTimeStr = DateUtil.formatDate(lastMission.getDate("in_time"));
                String beforeDateStr = DateUtil.formatDate(DateUtil.getDateBefore(new Date(), 1));
                if(lastMissionInTimeStr != null && lastMissionInTimeStr.equalsIgnoreCase(beforeDateStr)) {
                    day = lastMission.getInt("day") + 1;
                }
            }
            Random random = new Random();
            int score = random.nextInt(10) + 1;// 随机积分，1-10分
            user.set("score", user.getInt("score") + score)
                    .set("mission", DateUtil.formatDate(new Date())).update();
            getModel(Mission.class)
                    .set("score", score)
                    .set("author_id", user.get("id"))
                    .set("day", day)
                    .set("in_time", new Date()).save();
            setAttr("score", score);
            setAttr("day", day);
        } else {
            setAttr("msg", "msg");
        }
        setSessionAttr(Constants.USER_SESSION, user);
        render("front/mission/result.html");
    }

    public void top10() {
        setAttr("missionList", Mission.me.findTop10());
        render("front/mission/top10.html");
    }
}
