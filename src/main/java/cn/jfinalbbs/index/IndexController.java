package cn.jfinalbbs.index;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.system.Code;
import cn.jfinalbbs.topic.Topic;
import cn.jfinalbbs.user.AdminUser;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.*;
import cn.weibo.Users;
import cn.weibo.model.WeiboException;
import cn.weibo.util.BareBonesBrowserLaunch;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

public class IndexController extends BaseController {

    //首页
	public void index() {
        String tab = getPara("tab");
        String q = getPara("q");
        if(tab == null) tab = "all";
        Page<Topic> page = Topic.me.paginate(getParaToInt("p", 1), getParaToInt("size", 20), tab, q);
        setAttr("page", page);
        List<User> scoreTopTen = User.me.findBySize(10);
        setAttr("scoreTopTen", scoreTopTen);
        setAttr("tab", tab);
        setAttr("q", q);
        render("index.html");
	}

    //本地登录
    /*public void login() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            String email = getCookie(Constants.COOKIE_EMAIL);
            setAttr(Constants.COOKIE_EMAIL, email);
            render("user/login.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String email = getPara("email");
            String password = getPara("password");
            User user = User.me.localLogin(email, EncryptionUtil.md5Encrypt(password));
            if(user == null) {
                error("邮箱或密码错误");
            } else {
                // 记住邮箱
                String remember_me = getPara("remember_me");
                if("1".equalsIgnoreCase(remember_me)) {
                    setCookie(Constants.COOKIE_EMAIL, email, 60*60*24*30);
                }
                setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30*24*60*60);
                setSessionAttr(Constants.USER_SESSION, user);
                success();
            }
        }
    }

    //找回密码
    public void search_pass() throws Exception {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            render("user/search_pass.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String email = getPara("email");
            if(User.me.findByEmail(email) == null) {
                error("该邮箱未注册");
            } else {
                //发送邮件
                String code = StrUtil.getUUID();
                getModel(Code.class)
                        .set("code", code)
                        .set("in_time", new Date())
                        .set("expire_time", DateUtil.getDateAfter(new Date(), 1))
                        .set("status", 0)
                        .set("type", Constants.SystemCode.TYPE_SEARCH_PASS)
                        .set("target", email)
                        .save();
                EmailSender.getInstance().send("JFinal社区密码重置", new String[]{email}, "点击链接重置密码：" + Constants.getBaseUrl() + "/reset/" + code);
                success("我们已给您填写的电子邮箱发送了一封邮件，请在24小时内点击里面的链接来重置密码。");
            }
        }
    }

    //重置密码
    public void reset() {
        String method = getRequest().getMethod();
        String code = getPara(0);
        if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            code = getPara("code");
        }
        if (StrUtil.isBlank(code)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
                Code c = Code.me.findByCode(code, 0, new Date());
                if (c == null) {
                    renderText("链接失效");
                } else {
                    Code.me.updateByCode(code);
                    setAttr("code", code);
                    setAttr("email", c.get("target"));
                    render("user/reset.html");
                }
            } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
                String newPass = getPara("newPass");
                String email = getPara("email");
                int i = User.me.updateByEmail(email, EncryptionUtil.md5Encrypt(newPass));
                if(i == 0) error("重置失败");
                success("重置成功，快去登录吧！");
            }
        }
    }

    //本地注册
    public void register() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            render("user/register.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String nickname = getPara("nickname");
            String gender = getPara("gender");
            String password = getPara("password");
            String email = getPara("email");
            if(User.me.findByEmail(email) == null) {
                User user = new User();
                user.set("id", StrUtil.getUUID())
                    .set("nickname", nickname)
                    .set("token", StrUtil.getUUID())
                    .set("gender", gender)
                    .set("score", 0)
                    .set("mission", new Date())
                    .set("in_time", new Date())
                    .set("email", email)
                    .set("avatar", Constants.getBaseUrl() + "/static/img/default_avatar.png")
                    .set("password", EncryptionUtil.md5Encrypt(password))
                    .save();
                setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30*24*60*60);
                setSessionAttr(Constants.USER_SESSION, user);
                success();
            } else {
                error("邮箱已经注册过");
            }
        }
    }*/

    //登出
    public void logout() {
        removeCookie(Constants.USER_COOKIE);
        removeSessionAttr(Constants.USER_SESSION);
        redirect(Constants.getBaseUrl() + "/");
    }

    //跳转qq登录
    public void qqlogin() throws QQConnectException {
        redirect(new Oauth().getAuthorizeURL(getRequest()));
    }

    //qq登录回调方法
    public void qqlogincallback() throws QQConnectException {
        HttpServletRequest request = getRequest();
        AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
        String accessToken = null,openID = null;
        long tokenExpireIn = 0L;
        if (accessTokenObj.getAccessToken().equals("")) {
            renderText("用户取消了授权或没有获取到响应参数");
        } else {
            accessToken = accessTokenObj.getAccessToken();
            tokenExpireIn = accessTokenObj.getExpireIn();
            // 利用获取到的accessToken 去获取当前用的openid -------- start
            OpenID openIDObj =  new OpenID(accessToken);
            openID = openIDObj.getUserOpenID();
            User user = User.me.findByOpenID(openID, "qq");
            if (user == null) {
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean.getRet() == 0) {
                    String nickname = userInfoBean.getNickname();
                    String gender = userInfoBean.getGender();
                    String avatar = userInfoBean.getAvatar().getAvatarURL50();
                    Date expire_in = DateUtil.getDateAfter(new Date(), (int) tokenExpireIn / 60 / 60 / 24);
                    user = new User();
                    user.set("id", StrUtil.getUUID())
                        .set("nickname", nickname)
                        .set("token", StrUtil.getUUID())
                        .set("score", 0)
                        .set("gender", gender)
                        .set("avatar", avatar)
                        .set("open_id", openID)
                        .set("expire_time", expire_in)
                        .set("in_time", new Date())
                        .set("mission", new Date())
                        .set("thirdlogin_type", "qq").save();
                } else {
                    renderText("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
                }
            } else if(DateUtil.isExpire((Date) user.get("expire_time"))) {
                user.set("expire_time", tokenExpireIn).set("open_id", openID).update();
            }
            setSessionAttr(Constants.USER_SESSION, user);
            setCookie(Constants.USER_COOKIE, user != null ? StrUtil.getEncryptionToken(user.getStr("token")) : null, 30*24*60*60);
            String uri = getSessionAttr(Constants.BEFORE_URL);
            if(StrUtil.isBlank(uri)) {
                redirect(Constants.getBaseUrl() + "/");
            } else {
                redirect(uri);
            }
        }
    }

    //新浪微博登录
    public void weibologin() throws WeiboException, IOException {
        cn.weibo.Oauth oauth = new cn.weibo.Oauth();
//        BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
        redirect(oauth.authorize("code"));
    }

    //新浪微博登录后回调
    public void weibologincallback() throws WeiboException {
        String code = getPara("code");
        cn.weibo.Oauth oauth = new cn.weibo.Oauth();
        cn.weibo.http.AccessToken accessToken = oauth.getAccessTokenByCode(code);
        Users users = new Users(accessToken.getAccessToken());
        cn.weibo.model.User weiboUser = users.showUserById(accessToken.getUid());
        if(weiboUser != null) {
            String gender = "未知";
            if (weiboUser.getGender().equals("m")) {
                gender = "男";
            } else if (weiboUser.getGender().equals("f")) {
                gender = "女";
            }
            Date expire_in = DateUtil.getDateAfter(new Date(), Integer.parseInt(accessToken.getExpireIn()) / 60 / 60 / 24);
            User user = User.me.findByOpenID(weiboUser.getId(), "weibo_sina");
            if (user == null) {
                user = new User();
                user.set("id", StrUtil.getUUID())
                        .set("nickname", weiboUser.getScreenName())
                        .set("token", StrUtil.getUUID())
                        .set("score", 0)
                        .set("gender", gender)
                        .set("avatar", weiboUser.getAvatarLarge())
                        .set("open_id", weiboUser.getId())
                        .set("expire_time", expire_in)
                        .set("in_time", new Date())
                        .set("mission", new Date())
                        .set("thirdlogin_type", "weibo_sina").save();
            } else if (DateUtil.isExpire((Date) user.get("expire_time"))) {
                user.set("expire_time", expire_in).update();
            }
            setSessionAttr(Constants.USER_SESSION, user);
            setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
            redirect(Constants.getBaseUrl() + "/");
        } else {
            renderText("新浪微博登陆失败");
        }
    }

    //后台管理登录
    public void adminlogin() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            String userAdminToken = getCookie(Constants.COOKIE_ADMIN_TOKEN);
            if(!StrUtil.isBlank(userAdminToken)) {
                String[] namePwd = StrUtil.getDecryptToken(userAdminToken).split("@&@");
                setAttr("username", namePwd[0]);
                setAttr("password", namePwd[1]);
            }
            render("adminlogin.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String username = getPara("username");
            String password = getPara("password");
            int remember_me = getParaToInt("remember_me", 0);
            AdminUser adminUser = AdminUser.me.login(username, password);
            if(adminUser == null) {
                setAttr(Constants.ERROR, "用户名或密码错误");
                render("adminlogin.html");
            } else {
                setSessionAttr(Constants.SESSION_ADMIN_USER, adminUser);
                if(remember_me == 1) {
                    setCookie(Constants.COOKIE_ADMIN_TOKEN, StrUtil.getEncryptionToken(username + "@&@" + password), 30*24*60*60);
                }
                String before_url = getSessionAttr(Constants.ADMIN_BEFORE_URL);
                if(!StrUtil.isBlank(before_url) && !before_url.contains("adminlogin")) redirect(before_url);
                redirect(Constants.getBaseUrl() + "/admin/index");
            }
        }
    }

}