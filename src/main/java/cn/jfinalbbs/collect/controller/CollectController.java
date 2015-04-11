package cn.jfinalbbs.collect.controller;

import cn.jfinalbbs.collect.model.Collect;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.UserInterceptor;
import cn.jfinalbbs.user.model.User;
import cn.jfinalbbs.utils.Result;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.Date;

/**
 * Created by liuyang on 15/4/4.
 */
public class CollectController extends Controller {

    @Before(UserInterceptor.class)
    public void index() {
        String tid = getPara(0);
        User user = getSessionAttr(Constants.USER_SESSION);
        Collect collect = Collect.me.findByTidAndAuthorId(tid, user.getStr("id"));
        Result result = new Result(Constants.ResultCode.SUCCESS, Constants.ResultDesc.SUCCESS, null);
        if(collect != null) {
            result.setDescription("已经收藏过，无需再次收藏");
            renderJson(result);
        } else {
            collect = new Collect();
            boolean b = collect.set("tid", tid)
                    .set("author_id", user.get("id"))
                    .set("in_time", new Date()).save();
            if(!b) {
                result.setCode(Constants.ResultCode.FAILURE);
                result.setDescription(Constants.ResultDesc.FAILURE);
            }
            renderJson(result);
        }
    }

    @Before(UserInterceptor.class)
    public void delete() {
        String tid = getPara(0);
        User user = getSessionAttr(Constants.USER_SESSION);
        Collect collect = Collect.me.findByTidAndAuthorId(tid, user.getStr("id"));
        Result result = new Result(Constants.ResultCode.SUCCESS, Constants.ResultDesc.SUCCESS, null);
        if(collect == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            boolean b = Collect.me.deleteByTidAndAuthorId(tid, user.getStr("id"));
            if(!b) {
                result.setCode(Constants.ResultCode.FAILURE);
                result.setDescription("取消收藏失败");
            }
            renderJson(result);
        }
    }
}
