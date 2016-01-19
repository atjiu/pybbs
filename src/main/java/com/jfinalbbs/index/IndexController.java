package com.jfinalbbs.index;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.label.Label;
import com.jfinalbbs.reply.Reply;
import com.jfinalbbs.section.Section;
import com.jfinalbbs.topic.Topic;
import com.jfinalbbs.user.AdminUser;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.AgentUtil;
import com.jfinalbbs.utils.DateUtil;
import com.jfinalbbs.utils.EmailSender;
import com.jfinalbbs.utils.StrUtil;
import com.jfinalbbs.valicode.ValiCode;
import cn.weibo.Users;
import cn.weibo.model.WeiboException;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class IndexController extends BaseController {

    /**
     * 首页
     */
    public void index() {
        String tab = getPara("tab");
        String q = getPara("q");
        Integer l = getParaToInt("l");
        if (l != null) {
            tab = "all";
            setAttr("_label", Label.me.findById(l));
        }
        if (tab == null) {
            if (!StrUtil.isBlank(q)) {
                tab = "all";
            } else {
                Section section = Section.me.findDefault();
                tab = section != null ? section.getStr("tab") : "news";
            }
        }
        Page<Topic> page = Topic.me.paginate(getParaToInt("p", 1),
                getParaToInt("size", PropKit.use("config.properties").getInt("page_size")), tab, q, 1, l);
        for (Topic t : page.getList()) {
            t.put("labels", Label.me.findByTid(t.getStr("id")));
        }
        setAttr("page", page);
        List<User> scoreTopTen = User.me.findBySize(10);
        setAttr("scoreTopTen", scoreTopTen);
        setAttr("tab", tab);
        setAttr("q", q);
        setAttr("l", l);
        //查询无人回复的话题
        List<Topic> notReplyTopics = Topic.me.findNotReply(5);
        setAttr("notReplyTopics", notReplyTopics);
        //社区运行状态
        int userCount = User.me.countUsers();
        int topicCount = Topic.me.topicCount();
        int replyCount = Reply.me.replyCount();
        setAttr("userCount", userCount);
        setAttr("topicCount", topicCount);
        setAttr("replyCount", replyCount);
        if (!AgentUtil.getAgent(getRequest()).equals(AgentUtil.WEB)) render("mobile/index.html");
        else render("front/index.html");
    }

    /**
     * 登出
     */
    public void logout() {
        removeCookie(Constants.USER_COOKIE);
        removeSessionAttr(Constants.USER_SESSION);
        redirect(Constants.getBaseUrl() + "/");
    }

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
                redirect(Constants.getBaseUrl() + "/reg.html?third=qq");
            } else {
                setSessionAttr(Constants.USER_SESSION, user);
                setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
                String source = (String) getSession().getAttribute("source");
                if (!StrUtil.isBlank(source)) {
                    if (source.equalsIgnoreCase("usersetting")) {
                        getSession().removeAttribute("source");
                        redirect(Constants.getBaseUrl() + "/user/setting");
                    }
                } else {
                    redirect(Constants.getBaseUrl() + "/");
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
                    redirect(Constants.getBaseUrl() + "/reg.html?third=qq");
                } else {
                    setSessionAttr(Constants.USER_SESSION, user);
                    setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
                    String source = (String) getSession().getAttribute("source");
                    if (!StrUtil.isBlank(source)) {
                        if (source.equalsIgnoreCase("usersetting")) {
                            getSession().removeAttribute("source");
                            redirect(Constants.getBaseUrl() + "/user/setting");
                        }
                    } else {
                        redirect(Constants.getBaseUrl() + "/");
                    }
                }
            } else {
                renderText("新浪微博登陆失败");
            }
        }
    }

    /**
     * 后台管理登录
     * 默认账号admin
     * 默认密码123123
     * 对应表 admin_user
     */
    public void adminlogin() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            String userAdminToken = getCookie(Constants.COOKIE_ADMIN_TOKEN);
            if (!StrUtil.isBlank(userAdminToken)) {
                String[] namePwd = StrUtil.getDecryptToken(userAdminToken).split("@&@");
                setAttr("username", namePwd[0]);
                setAttr("password", namePwd[1]);
            }
            render("front/adminlogin.html");
        } else if (method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String username = getPara("username");
            String password = getPara("password");
            int remember_me = getParaToInt("remember_me", 0);
            AdminUser adminUser = AdminUser.me.login(username, HashKit.md5(password));
            if (adminUser == null) {
                setAttr(Constants.ERROR, "用户名或密码错误");
                render("front/adminlogin.html");
            } else {
                setSessionAttr(Constants.SESSION_ADMIN_USER, adminUser);
                if (remember_me == 1) {
                    setCookie(Constants.COOKIE_ADMIN_TOKEN, StrUtil.getEncryptionToken(username + "@&@" + HashKit.md5(password)), 30 * 24 * 60 * 60);
                }
                String before_url = getSessionAttr(Constants.ADMIN_BEFORE_URL);
                if (!StrUtil.isBlank(before_url) && !before_url.contains("adminlogin")) redirect(before_url);
                redirect(Constants.getBaseUrl() + "/admin/index");
            }
        }
    }

    /**
     * Api入口
     */
    public void api() {
        render("front/api.html");
    }

    /**
     * 文档入口
     */
    public void doc() {
        render("front/doc.html");
    }

    public void login() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            if (!AgentUtil.getAgent(getRequest()).equals(AgentUtil.WEB)) render("mobile/user/login.html");
        } else if (method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String email = getPara("email");
            String password = getPara("password");
            if (StrUtil.isBlank(email) || StrUtil.isBlank(password)) {
                error("用户名或密码都不能为空");
            } else {
                User user = User.me.localLogin(email, HashKit.md5(password));
                if (user == null) {
                    error("用户名或密码错误");
                } else {
                    setSessionAttr(Constants.USER_SESSION, user);
                    setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
                    success();
                }
            }
        }
    }

    public void reg() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            String third = getPara("third");
            if (StrUtil.isBlank(third)) {
                removeSessionAttr("open_id");
                removeSessionAttr("thirdlogin_type");
                removeSessionAttr("unsave_user");
            }
            if (!AgentUtil.getAgent(getRequest()).equals(AgentUtil.WEB)) render("mobile/user/reg.html");
            else render("front/user/reg.html");
        } else if (method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String email = getPara("reg_email");
            String password = getPara("reg_password");
            String nickname = getPara("reg_nickname");
            String valicode = getPara("valicode");
            String open_id = (String) getSession().getAttribute("open_id");
            if (StrUtil.isBlank(email) || StrUtil.isBlank(password) || StrUtil.isBlank(nickname) || StrUtil.isBlank(valicode)) {
                error("请完善注册信息");
            } else {
                if (!StrUtil.isEmail(email)) {
                    error("请输入正确的邮箱地址");
                } else {
                    ValiCode code = ValiCode.me.findByCodeAndEmail(valicode, email, Constants.ValiCodeType.REG);
                    if (code == null) {
                        error("验证码不存在或已使用(已过期)");
                    } else {
                        User user = User.me.findByEmail(email);
                        if (user != null) {
                            error("邮箱已经注册，请直接登录");
                        } else if (User.me.findByNickname(nickname) != null) {
                            error("昵称已经被注册，请更换其他昵称");
                        } else {
                            String uuid = StrUtil.getUUID();
                            String token = StrUtil.getUUID();
                            Date date = new Date();
                            if (StrUtil.isBlank(open_id)) {
                                user = new User();
                                user.set("id", uuid)
                                        .set("nickname", StrUtil.noHtml(nickname).trim())
                                        .set("password", HashKit.md5(password))
                                        .set("score", 0)
                                        .set("mission", date)
                                        .set("in_time", date)
                                        .set("email", email)
                                        .set("token", token)
                                        .set("avatar", Constants.getBaseUrl() + "/static/img/default_avatar.png")
                                        .save();
                            } else {
                                user = getSessionAttr("unsave_user");
                                if (user == null) {
                                    user = new User();
                                    user.set("id", uuid)
                                            .set("nickname", StrUtil.noHtml(nickname).trim())
                                            .set("password", HashKit.md5(password))
                                            .set("score", 0)
                                            .set("mission", date)
                                            .set("in_time", date)
                                            .set("email", email)
                                            .set("token", token)
                                            .set("avatar", Constants.getBaseUrl() + "/static/img/default_avatar.png")
                                            .save();
                                } else {
                                    user.set("nickname", StrUtil.noHtml(nickname).trim())
                                            .set("password", HashKit.md5(password))
                                            .set("mission", date)
                                            .set("email", email)
                                            .set("token", token)
                                            .set("in_time", date)
                                            .set("score", 0)
                                            .set("avatar", Constants.getBaseUrl() + "/static/img/default_avatar.png")
                                            .save();
                                }
                                removeSessionAttr("unsave_user");
                                removeSessionAttr("open_id");
                                removeSessionAttr("thirdlogin_type");
                            }
                            setSessionAttr(Constants.USER_SESSION, user);
                            setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
                            //更新验证状态
                            code.set("status", 1).update();
                            success();
                        }
                    }
                }
            }
        }
    }

    public void sendValiCode() {
        String email = getPara("email");
        if (StrUtil.isBlank(email)) {
            error("邮箱不能为空");
        } else if (!StrUtil.isEmail(email)) {
            error("邮箱格式不正确");
        } else {
            String type = getPara("type");
            String valicode = StrUtil.randomString(6);
            if (type.equalsIgnoreCase(Constants.ValiCodeType.FORGET_PWD)) {
                User user = User.me.findByEmail(email);
                if (user == null) {
                    error("改邮箱未被注册，请先注册");
                } else {
                    ValiCode code = new ValiCode();
                    code.set("code", valicode)
                            .set("type", type)
                            .set("in_time", new Date())
                            .set("status", 0)
                            .set("expire_time", DateUtil.getMinuteAfter(new Date(), 30))
                            .set("target", email)
                            .save();
                    EmailSender.sendMail("JFinalbbs－找回密码验证码", new String[]{email}, "您找回密码的验证码是：" + valicode + "\r\n" + "该验证码只能使用一次，并且有效期仅30分钟。");
                    success();
                }
            } else if (type.equalsIgnoreCase(Constants.ValiCodeType.REG)) {
                User user = User.me.findByEmail(email);
                if (user != null) {
                    error("邮箱已经注册，请直接登录");
                } else {
                    ValiCode code = new ValiCode();
                    code.set("code", valicode)
                            .set("type", type)
                            .set("in_time", new Date())
                            .set("status", 0)
                            .set("expire_time", DateUtil.getMinuteAfter(new Date(), 30))
                            .set("target", email)
                            .save();
                    EmailSender.sendMail("JFinalbbs－注册账户验证码", new String[]{email}, "您注册账户的验证码是：" + valicode + "\r\n" + "该验证码只能使用一次，并且有效期仅30分钟。");
                    success();
                }
            }
        }
    }

    public void forgetpwd() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            if (!AgentUtil.getAgent(getRequest()).equals(AgentUtil.WEB)) render("mobile/user/forgetpwd.html");
            else render("front/user/forgetpwd.html");
        } else if (method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String email = getPara("email");
            String valicode = getPara("valicode");
            String newpwd = getPara("newpwd");
            if (StrUtil.isBlank(email) || StrUtil.isBlank(valicode) || StrUtil.isBlank(newpwd)) {
                error("请完善信息");
            } else {
                ValiCode code = ValiCode.me.findByCodeAndEmail(valicode, email, Constants.ValiCodeType.FORGET_PWD);
                if (code == null) {
                    error("验证码不存在或已使用(已过期)");
                } else {
                    User user = User.me.findByEmail(email);
                    if (user == null) {
                        error("改邮箱未被注册，请先注册");
                    } else {
                        user.set("password", HashKit.md5(newpwd)).update();
                        code.set("status", 1).update();
                        success();
                    }
                }
            }
        }
    }

    public void donate() {
        render("front/donate.html");
    }

}