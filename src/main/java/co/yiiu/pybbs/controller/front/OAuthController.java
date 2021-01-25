package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.model.OAuthUser;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.plugin.SocialPlugin;
import co.yiiu.pybbs.service.IOAuthUserService;
import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.service.IUserService;
import co.yiiu.pybbs.util.CookieUtil;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/oauth")
public class OAuthController extends BaseController {

    @Resource
    private ISystemConfigService systemConfigService;
    @Resource
    private IUserService userService;
    @Resource
    private IOAuthUserService oAuthUserService;
    @Resource
    private CookieUtil cookieUtil;
    @Resource
    private SocialPlugin socialPlugin;

    @GetMapping("/redirect/{type}")
    public String github(@PathVariable("type") String type, HttpSession session) {

        AuthRequest request = socialPlugin.getRequest(type);

        return redirect(request.authorize(AuthStateUtils.createState()));
    }

    @GetMapping("/{type}/callback")
    public String callback(@PathVariable("type") String type, AuthCallback callback, HttpSession session) {

        AuthRequest request = socialPlugin.getRequest(type);

        AuthResponse<AuthUser> response = request.login(callback);
        if (!response.ok()) {
            throw new IllegalArgumentException(response.getMsg());
        }
        AuthUser authUser = response.getData();

        String username = authUser.getUsername();
        String githubId = authUser.getUuid();

        // 拿用户信息
        String avatarUrl = authUser.getAvatar();
        String bio = authUser.getRemark();
        String email = authUser.getEmail();
        String blog = authUser.getBlog();
        String accessToken = authUser.getToken().getAccessToken();

        OAuthUser oAuthUser = oAuthUserService.selectByTypeAndLogin(type.toUpperCase(), authUser.getUsername());
        User user;
        if (oAuthUser == null) {
            if (userService.selectByUsername(username) != null) {
                username = username + githubId;
            }
            // 先创建user，然后再创建oauthUser
            user = userService.addUser(username, null, avatarUrl, email, bio, blog, StringUtils.isEmpty(email));
            oAuthUserService.addOAuthUser(Integer.parseInt(githubId), type.toUpperCase(), authUser.getUsername(), accessToken, bio, email, user.getId
                    (), null, null, null);
        } else {
            user = userService.selectById(oAuthUser.getUserId());
            oAuthUser.setEmail(email);
            oAuthUser.setBio(bio);
            oAuthUser.setAccessToken(accessToken);
            oAuthUserService.update(oAuthUser);
        }

        // 将用户信息写session
        session.setAttribute("_user", user);
        // 将用户token写cookie
        cookieUtil.setCookie(systemConfigService.selectAllConfig().get("cookie_name").toString(), user.getToken());

        return redirect("/");
    }
}
