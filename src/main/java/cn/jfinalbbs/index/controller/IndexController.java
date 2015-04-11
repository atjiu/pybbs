package cn.jfinalbbs.index.controller;

import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.topic.model.Topic;
import cn.jfinalbbs.user.model.AdminUser;
import cn.jfinalbbs.user.model.User;
import cn.jfinalbbs.utils.DateUtil;
import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public class IndexController extends Controller {

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
//		setSessionAttr(Constants.USER_SESSION, User.me.findById("c6c8f3ac147b491eb6457cbcf53cb17f"));
        render("index.html");
	}

    public void logout() {
        removeCookie(Constants.USER_COOKIE);
        removeSessionAttr(Constants.USER_SESSION);
        redirect("/");
    }

    public void qqlogin() throws QQConnectException {
        redirect(new Oauth().getAuthorizeURL(getRequest()));
    }

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
                        .set("score", 100)
                        .set("gender", gender)
                        .set("avatar", avatar)
                        .set("token", openID)
                        .set("expire_time", expire_in)
                        .set("in_time", new Date())
                        .set("thirdlogin_type", "qq").save();
                } else {
                    renderText("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
                }
            } else if(DateUtil.isExpire((Date) user.get("expire_time"))) {
                user.set("expire_time", tokenExpireIn).set("token", openID).update();
            }
            setSessionAttr(Constants.USER_SESSION, user);
            setCookie(Constants.USER_COOKIE, user != null ? user.getStr("id") : null, 30*24*60*60);
            String uri = getSessionAttr(Constants.BEFORE_URL);
            System.out.println(uri + " uri's value");
            if(StrUtil.isBlank(uri)) {
                redirect("/");
            } else {
                redirect(uri);
            }
        }
    }

    public void adminlogin() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase("get")) {
            String username = getCookie(Constants.COOKIE_USERNAME);
            setAttr("username", username);
            render("adminlogin.html");
        } else if(method.equalsIgnoreCase("post")) {
            String username = getPara("username");
            String password = getPara("password");
            int remember_me = getParaToInt("remember_me", 0);
            AdminUser adminUser = AdminUser.me.login(username, password);
            if(adminUser == null) {
                setAttr(Constants.ERROR, "用户名或密码错误");
                render("adminlogin.html");
            } else {
                setSessionAttr(Constants.ADMIN_USER_SESSION, adminUser);
                if(remember_me == 1) {
                    setCookie(Constants.COOKIE_USERNAME, username, 30*24*60*60);
                }
                redirect("/admin/index");
            }
        }
    }

//    public void createSiteMap() {
//        List<Topic> topics = Topic.me.find("select * from topic");
//        StringBuffer sb = new StringBuffer();
//        for(Topic topic: topics) {
//            sb.append("<url><loc>");
//            sb.append("http://jfinalbbs.liygheart.com/topic/"+topic.get("id")+".html");
//            sb.append("</loc><priority>0.5</priority><lastmod>2015-04-09</lastmod><changefreq>always</changefreq></url>");
//        }
//        System.out.println(sb.toString());
//        renderText(sb.toString());
//    }

}



