package co.yiiu.web.front;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiException;
import co.yiiu.core.util.identicon.Identicon;
import co.yiiu.core.util.security.Base64Helper;
import co.yiiu.module.score.model.ScoreLog;
import co.yiiu.module.score.service.ScoreLogService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private Identicon identicon;
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private ScoreLogService scoreLogService;

    /**
     * 个人资料
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}")
    public String profile(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "front/user/info";
    }

    /**
     * 用户发布的所有话题
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/topics")
    public String topics(@PathVariable String username, Integer p, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("p", p);
        return "front/user/topics";
    }

    /**
     * 用户发布的所有回复
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/replies")
    public String replies(@PathVariable String username, Integer p, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("p", p);
        return "front/user/replies";
    }

    /**
     * 用户收藏的所有话题
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/collects")
    public String collects(@PathVariable String username, Integer p, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("p", p);
        return "front/user/collects";
    }

    /**
     * 进入用户个人设置页面
     *
     * @param model
     * @return
     */
    @GetMapping("/profile")
    public String setting(Model model) {
        model.addAttribute("user", getUser());
        return "front/user/setting/profile";
    }

    /**
     * 更新用户的个人设置
     *
     * @param email
     * @param url
     * @param bio
     * @param response
     * @return
     */
    @PostMapping("/profile")
    public String updateUserInfo(String email, String url, String bio, HttpServletResponse response) throws Exception {
        User user = getUser();
        if (user.isBlock())
            throw new Exception("你的帐户已经被禁用，不能进行此项操作");
        user.setEmail(email);
        if (bio != null && bio.trim().length() > 0) user.setBio(bio);
        user.setUrl(url);
        userService.save(user);
        return redirect(response, "/user/" + user.getUsername());
    }

    /**
     * 修改头像
     *
     * @param model
     * @return
     */
    @GetMapping("/changeAvatar")
    public String changeAvatar(Model model) {
        model.addAttribute("user", getUser());
        return "front/user/setting/changeAvatar";
    }

    /**
     * 保存头像
     *
     * @param avatar
     * @return
     * @throws ApiException
     */
    @PostMapping("changeAvatar")
    @ResponseBody
    public Result changeAvatar(String avatar) throws ApiException, IOException {
        if (StringUtils.isEmpty(avatar)) throw new ApiException("头像不能为空");
        String _avatar = avatar.substring(avatar.indexOf(",") + 1, avatar.length());
        User user = getUser();
        byte[] bytes;
        try {
            bytes = Base64Helper.decode(_avatar);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("头像格式不正确");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(bais);
        String __avatar = identicon.saveFile(user.getUsername(), bufferedImage);
        user.setAvatar(__avatar);
        userService.save(user);
        bais.close();
        return Result.success();
    }

    @GetMapping("/changePassword")
    public String changePassword() {
        return "front/user/setting/changePassword";
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @PostMapping("/changePassword")
    public String changePassword(String oldPassword, String newPassword, Model model) throws Exception {
        User user = getUser();
        if (user.isBlock())
            throw new Exception("你的帐户已经被禁用，不能进行此项操作");
        if (new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userService.save(user);
            model.addAttribute("changePasswordErrorMsg", "修改成功，请重新登录");
        } else {
            model.addAttribute("changePasswordErrorMsg", "旧密码不正确");
        }
        model.addAttribute("user", getUser());
        return "front/user/setting/changePassword";
    }

    /**
     * user accessToken page
     *
     * @param model
     * @return
     */
    @GetMapping("/accessToken")
    public String accessToken(Model model) {
        model.addAttribute("user", getUser());
        return "/front/user/setting/accessToken";
    }

    /**
     * 刷新token
     *
     * @param response
     * @return
     */
    @GetMapping("/refreshToken")
    public String refreshToken(HttpServletResponse response) {
        User user = getUser();
        user.setToken(UUID.randomUUID().toString());
        userService.save(user);
        return redirect(response, "/user/accessToken");
    }

    /**
     * user upload file list
     *
     * @param model
     * @return
     */
    @GetMapping("/space")
    public String space(Model model) {
        long count = 0;
        String userUploadPath = getUsername() + "/";
        List list = new ArrayList();
        File file = new File(siteConfig.getUploadPath() + userUploadPath);
        if (file.exists()) {
            for (File f : file.listFiles()) {
                Map map = new HashMap();
                if (f.isDirectory() && f.listFiles().length > 0) {
                    String dirName = f.getName();
                    List fileList = new ArrayList();
                    for (File f1 : f.listFiles()) {
                        count += f1.length();
                        Map m = new HashMap();
                        m.put("fileName", f1.getName());
                        m.put("fileUrl", siteConfig.getStaticUrl() + userUploadPath + dirName + "/" + f1.getName());
                        fileList.add(m);
                    }
                    map.put("dirName", dirName);
                    map.put("fileList", fileList);
                    list.add(map);
                }
            }
        }
        model.addAttribute("user", getUser());
        model.addAttribute("count", (double) count / 1000000);
        model.addAttribute("list", list);
        return "front/user/setting/space";
    }

    /**
     * query user score history
     *
     * @param p     page
     * @param model
     * @return
     */
    @GetMapping("/scoreLog")
    public String scoreLog(Integer p, Model model) {
        User user = getUser();

        model.addAttribute("scoreLogs", scoreLogService.findScoreByUser(p, siteConfig.getPageSize(), user));

        return "front/user/setting/scoreLog";
    }


}
