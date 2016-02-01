package com.jfinalbbs.oauth;

import cn.weibo.Users;
import cn.weibo.model.WeiboException;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.StrUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class OauthController extends BaseController {

    /**
     * 跳转qq登录
     *
     * @throws QQConnectException
     */
    public void qqlogin() throws QQConnectException {
        String source = getPara("source");
        if (!StrUtil.isBlank(source)) {
            getSession().setAttribute("source", source);
        }
        redirect(new Oauth().getAuthorizeURL(getRequest()));
    }

    /**
     * qq登录回调方法
     *
     * @throws QQConnectException
     */
    public void qqlogincallback() throws QQConnectException, IOException {
        HttpServletRequest request = getRequest();
        AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
        String accessToken = null, openID = null;
        if (accessTokenObj.getAccessToken().equals("")) {
            renderText("用户取消了授权或没有获取到响应参数");
        } else {
            accessToken = accessTokenObj.getAccessToken();
            // 利用获取到的accessToken 去获取当前用的openid -------- start
            OpenID openIDObj = new OpenID(accessToken);
            openID = openIDObj.getUserOpenID();
            UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
            UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
            String avatar = userInfoBean.getAvatar().getAvatarURL50();
            String nickname = userInfoBean.getNickname();
            System.out.println(nickname);
            User user = (User) getSession().getAttribute(Constants.USER_SESSION);
            if (user == null) {
                user = User.me.findByOpenID(openID, "qq");
                String id = StrUtil.getUUID();
                if (user == null) {
                    user = new User();
                    user.set("id", id)
                            .set("qq_nickname", nickname)
                            .set("qq_avatar", avatar)
                            .set("qq_open_id", openID);
                } else {
                    user.set("qq_nickname", nickname)
                            .set("qq_avatar", avatar);
                }
                setSessionAttr("open_id", openID);
                setSessionAttr("thirdlogin_type", "qq");
                setSessionAttr("unsave_user", user);
            } else {
                User user1 = User.me.findByOpenID(openID, "qq");
                if (user1 != null) {
                    getResponse().setCharacterEncoding("utf-8");
                    getResponse().getWriter().write("<script>alert('此QQ号已经绑定其他账户,请更换绑定');location.href=\'/user/setting\'</script>");
                    return;
                } else {
                    user.set("qq_nickname", nickname)
                            .set("qq_open_id", openID)
                            .set("qq_avatar", avatar)
                            .update();
                }
            }
            if (StrUtil.isBlank(user.getStr("email"))) {
                redirect(baseUrl() + "/reg.html?third=qq");
            } else {
                setSessionAttr(Constants.USER_SESSION, user);
                setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
                String source = (String) getSession().getAttribute("source");
                if (!StrUtil.isBlank(source)) {
                    if (source.equalsIgnoreCase("usersetting")) {
                        getSession().removeAttribute("source");
                        redirect(baseUrl() + "/user/setting");
                    }
                } else {
                    redirect(baseUrl() + "/");
                }
            }
        }
    }

    /**
     * 新浪微博登录
     *
     * @throws WeiboException
     * @throws IOException
     */
    public void weibologin() throws WeiboException, IOException {
        String source = getPara("source");
        if (!StrUtil.isBlank(source)) {
            getSession().setAttribute("source", source);
        }
        cn.weibo.Oauth oauth = new cn.weibo.Oauth();
        redirect(oauth.authorize("code"));
    }

    /**
     * 新浪微博登录后回调
     *
     * @throws WeiboException
     */
    public void weibologincallback() throws WeiboException, IOException {
        String code = getPara("code");
        cn.weibo.Oauth oauth = new cn.weibo.Oauth();
        String error = getPara("error");
        if (!StrUtil.isBlank(error) && error.equals("access_denied")) {
            renderText("用户拒绝了新浪微博登录");
        } else {
            cn.weibo.http.AccessToken accessToken = oauth.getAccessTokenByCode(code);
            Users users = new Users(accessToken.getAccessToken());
            cn.weibo.model.User weiboUser = users.showUserById(accessToken.getUid());
            if (weiboUser != null) {
                User user = (User) getSession().getAttribute(Constants.USER_SESSION);
                if (user == null) {
                    user = User.me.findByOpenID(weiboUser.getId(), "sina");
                    String id = StrUtil.getUUID();
                    if (user == null) {
                        user = new User();
                        user.set("id", id)
                                .set("sina_nickname", weiboUser.getScreenName())
                                .set("sina_avatar", weiboUser.getAvatarLarge())
                                .set("sina_open_id", weiboUser.getId());
                    } else {
                        user.set("sina_nickname", weiboUser.getScreenName())
                                .set("sina_avatar", weiboUser.getAvatarLarge());
                    }
                    setSessionAttr("open_id", weiboUser.getId());
                    setSessionAttr("thirdlogin_type", "sina");
                    setSessionAttr("unsave_user", user);
                } else {
                    User user1 = User.me.findByOpenID(weiboUser.getId(), "sina");
                    if (user1 != null) {
                        getResponse().setCharacterEncoding("utf-8");
                        getResponse().getWriter().write("<script>alert('此微博账号已经绑定其他账户,请更换绑定');location.href=\'/user/setting\'</script>");
                        return;
                    } else {
                        user.set("sina_nickname", weiboUser.getScreenName())
                                .set("sina_avatar", weiboUser.getAvatarLarge())
                                .set("sina_open_id", weiboUser.getId()).update();
                    }
                }
                if (StrUtil.isBlank(user.getStr("email"))) {
                    redirect(baseUrl() + "/reg.html?third=qq");
                } else {
                    setSessionAttr(Constants.USER_SESSION, user);
                    setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
                    String source = (String) getSession().getAttribute("source");
                    if (!StrUtil.isBlank(source)) {
                        if (source.equalsIgnoreCase("usersetting")) {
                            getSession().removeAttribute("source");
                            redirect(baseUrl() + "/user/setting");
                        }
                    } else {
                        redirect(baseUrl() + "/");
                    }
                }
            } else {
                renderText("新浪微博登陆失败");
            }
        }
    }

}
