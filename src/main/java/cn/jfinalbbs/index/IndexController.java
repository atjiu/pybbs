package cn.jfinalbbs.index;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.topic.Topic;
import cn.jfinalbbs.user.AdminUser;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.utils.AgentUtil;
import cn.jfinalbbs.utils.DateUtil;
import cn.jfinalbbs.utils.StrUtil;
import cn.weibo.Users;
import cn.weibo.model.WeiboException;
import com.jfinal.kit.FileKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
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

public class IndexController extends BaseController {

    /**
     * 首页
     */
	public void index() {
        String tab = getPara("tab");
        String q = getPara("q");
        if(tab == null) tab = "all";
        Page<Topic> page = Topic.me.paginate(getParaToInt("p", 1), getParaToInt("size", 20), tab, q, 1);
        setAttr("page", page);
        List<User> scoreTopTen = User.me.findBySize(10);
        setAttr("scoreTopTen", scoreTopTen);
        setAttr("tab", tab);
        setAttr("q", q);
        if(!AgentUtil.getAgent(getRequest()).equals(AgentUtil.WEB)) render("mobile/index.html");
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
     * @throws QQConnectException
     */
    public void qqlogin() throws QQConnectException {
        redirect(new Oauth().getAuthorizeURL(getRequest()));
    }

    /**
     * qq登录回调方法
     * @throws QQConnectException
     */
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
                    if(StrUtil.isBlank(nickname)) nickname = "jf_" + StrUtil.randomString(6);
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

    /**
     * 新浪微博登录
     * @throws WeiboException
     * @throws IOException
     */
    public void weibologin() throws WeiboException, IOException {
        cn.weibo.Oauth oauth = new cn.weibo.Oauth();
        redirect(oauth.authorize("code"));
    }

    /**
     * 新浪微博登录后回调
     * @throws WeiboException
     */
    public void weibologincallback() throws WeiboException {
        String code = getPara("code");
        cn.weibo.Oauth oauth = new cn.weibo.Oauth();
        String error = getPara("error");
        if(!StrUtil.isBlank(error) && error.equals("access_denied")) {
            renderText("用户拒绝了新浪微博登录");
        } else {
            cn.weibo.http.AccessToken accessToken = oauth.getAccessTokenByCode(code);
            Users users = new Users(accessToken.getAccessToken());
            cn.weibo.model.User weiboUser = users.showUserById(accessToken.getUid());
            if (weiboUser != null) {
                String gender = "未知";
                if (weiboUser.getGender().equals("m")) {
                    gender = "男";
                } else if (weiboUser.getGender().equals("f")) {
                    gender = "女";
                }
                Date expire_in = DateUtil.getDateAfter(new Date(), Integer.parseInt(accessToken.getExpireIn()) / 60 / 60 / 24);
                User user = User.me.findByOpenID(weiboUser.getId(), "weibo_sina");
                if (user == null) {
                    String nickname = weiboUser.getScreenName();
                    if (StrUtil.isBlank(nickname)) nickname = "jf_" + StrUtil.randomString(6);
                    user = new User();
                    user.set("id", StrUtil.getUUID())
                            .set("nickname", nickname)
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
    }

    /**
     * 后台管理登录
     * 默认账号admin
     * 默认密码123123
     * 对应表 admin_user
     */
    public void adminlogin() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            String userAdminToken = getCookie(Constants.COOKIE_ADMIN_TOKEN);
            if(!StrUtil.isBlank(userAdminToken)) {
                String[] namePwd = StrUtil.getDecryptToken(userAdminToken).split("@&@");
                setAttr("username", namePwd[0]);
                setAttr("password", namePwd[1]);
            }
            render("front/adminlogin.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String username = getPara("username");
            String password = getPara("password");
            int remember_me = getParaToInt("remember_me", 0);
            AdminUser adminUser = AdminUser.me.login(username, password);
            if(adminUser == null) {
                setAttr(Constants.ERROR, "用户名或密码错误");
                render("front/adminlogin.html");
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

    /**
     * markdown图片本地上传
     */
    public void localupload() {
        UploadFile uploadFile = getFile();
        String path = Constants.getBaseUrl() + "/" + Constants.UPLOAD_DIR + "/" + uploadFile.getFileName();
        renderText(path);
    }

    /**
     * markdown图片上传七牛云
     * 需要在config.properties配置文件里配置一下七牛的key,secret等信息
     * @throws QiniuException
     */
    public void qiniuupload() throws QiniuException {
        Prop prop = PropKit.use("config.properties");
        Auth auth = Auth.create(prop.get("qiniu.access_key"), prop.get("qiniu.secret_key"));
        String token = auth.uploadToken(prop.get("qiniu.bucket"));
        UploadManager uploadManager = new UploadManager();
        UploadFile uploadFile = getFile();
        // 上传文件添加时间戳，防止文件重名，七牛报错
        String datetime = DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss");
        String fileName = uploadFile.getFileName().substring(0, uploadFile.getFileName().lastIndexOf("."));
        String suffix = uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf("."), uploadFile.getFileName().length());
        String _fileName = fileName + datetime + suffix;
        uploadManager.put(uploadFile.getFile(), _fileName, token);
        String path = prop.get("qiniu.url") + "/" + _fileName;
        // 删除保存在本地的文件
        FileKit.delete(uploadFile.getFile());
        renderText(path);
    }

    /**
     * 线上Api入口
     */
    public void api() {
        render("front/api.html");
    }
}