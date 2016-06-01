package cn.tomoya.module.oauth;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.module.system.Role;
import cn.tomoya.module.system.UserRole;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.DateUtil;
import cn.tomoya.utils.StrUtil;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://bbs.tomoya.cn
 */
@ControllerBind(controllerKey = "/oauth", viewPath = "WEB-INF/page")
public class OauthController extends BaseController {

    private static final String STATE = "githublogin_state";

    public void githublogin() throws UnsupportedEncodingException {
        LogKit.info("githublogin");
        String state = StrUtil.randomString(6);
        setCookie(STATE, state, 5 * 60, "/", PropKit.get("cookie.domain"), true);
        StringBuffer sb = new StringBuffer();
        sb.append("https://github.com/login/oauth/authorize")
                .append("?")
                .append("client_id")
                .append("=")
                .append(PropKit.get("github.client_id"))
                .append("&")
                .append("state")
                .append("=")
                .append(state)
                .append("&")
                .append("scope")
                .append("=")
                .append("user");
        redirect(sb.toString());
    }

    public void githubcallback() throws UnsupportedEncodingException {
        String code = getPara("code");
        String state = getPara("state");
        String cookieState = getCookie(STATE);
        if (state.equalsIgnoreCase(cookieState)) {
//            请求access_token
            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("client_id", PropKit.get("github.client_id"));
            map1.put("client_secret", PropKit.get("github.client_secret"));
            map1.put("code", code);
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            String resp1 = HttpKit.post("https://github.com/login/oauth/access_token", map1, "", headers);
            Map respMap1 = StrUtil.parseToMap(resp1);
            //access_token, scope, token_type
            String github_access_token = (String) respMap1.get("access_token");
            //获取用户信息
            Map<String, String> map2 = new HashMap<String, String>();
            map2.put("access_token", github_access_token);
            String resp2 = HttpKit.get("https://api.github.com/user", map2);
            Map respMap2 = StrUtil.parseToMap(resp2);
            Double githubId = (Double) respMap2.get("id");
            String login = (String) respMap2.get("login");
            String avatar_url = (String) respMap2.get("avatar_url");
            String email = (String) respMap2.get("email");
            String html_url = (String) respMap2.get("html_url");

            Date now = new Date();
            String access_token = StrUtil.getUUID();
            User user = User.me.findByGithubId(String.valueOf(githubId));
            boolean flag = true;
            if (user == null) {
                user = new User();
                user.set("in_time", now)
                        .set("score", 0)
                        .set("github_id", String.valueOf(githubId))
                        .set("isblock", false)
                        .set("receive_msg", true);//邮箱接收社区消息
                flag = false;
            }
            user.set("nickname", login)
                    .set("access_token", access_token)
                    .set("avatar", avatar_url)
                    .set("email", email)
                    .set("url", html_url)
                    .set("expire_time", DateUtil.getDateAfter(now, 30));//30天后过期,要重新认证
            if (flag) {
                user.update();
            } else {
                user.save();
                //新注册的用户角色都是普通用户
                Role role = Role.me.findByName("user");
                if(role != null) {
                    UserRole userRole = new UserRole();
                    userRole.set("uid", user.getInt("id"))
                            .set("rid", role.getInt("id"))
                            .save();
                }
            }
            setCookie(Constants.USER_ACCESS_TOKEN,
                    StrUtil.getEncryptionToken(user.getStr("access_token")),
                    30 * 24 * 60 * 60, "/",
                    PropKit.get("cookie.domain"),
                    true);
            String callback = getPara("callback");
            if (StrUtil.notBlank(callback)) {
                callback = URLDecoder.decode(callback, "UTF-8");
            }
            redirect(StrUtil.notBlank(callback) ? callback : "/");
        } else {
            renderText(Constants.OP_ERROR_MESSAGE);
        }
    }

}
