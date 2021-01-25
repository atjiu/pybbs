package co.yiiu.pybbs.service.impl;

import co.yiiu.pybbs.mapper.UserMapper;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.*;
import co.yiiu.pybbs.util.MyPage;
import co.yiiu.pybbs.util.SpringContextUtil;
import co.yiiu.pybbs.util.StringUtil;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import co.yiiu.pybbs.util.identicon.Identicon;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class UserService implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    @Lazy
    private ICollectService collectService;
    @Resource
    @Lazy
    private ITopicService topicService;
    @Resource
    @Lazy
    private ICommentService commentService;
    @Resource
    private Identicon identicon;
    @Resource
    private INotificationService notificationService;
    @Resource
    private ISystemConfigService systemConfigService;
    @Resource
    private ICodeService codeService;

    // 根据用户名查询用户，用于获取用户的信息比对密码
    @Override
    public User selectByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    // 递归生成token，防止token重复
    // 理论上uuid生成的token是不可能重复的
    // 加个逻辑放心 : )
    private String generateToken() {
        String token = UUID.randomUUID().toString();
        User user = this.selectByToken(token);
        if (user != null) {
            return this.generateToken();
        }
        return token;
    }

    /**
     * 注册创建用户
     *
     * @param username
     * @param password
     * @param avatar
     * @param email
     * @param bio
     * @param website
     * @param needActiveEmail 是否需要发送激活邮件
     *                        新注册的用户都需要
     *                        Github注册的用户，如果能获取到邮箱，就自动激活，如果获取不到邮箱，则是未激活状态，需要用户绑定邮箱然后发送激活邮件进行激活
     * @return
     */
    @Override
    public User addUser(String username, String password, String avatar, String email, String bio, String website,
                        boolean needActiveEmail) {
        String token = this.generateToken();
        User user = new User();
        user.setUsername(username);
        if (!StringUtils.isEmpty(password)) user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setToken(token);
        user.setInTime(new Date());
        if (avatar == null) avatar = identicon.generator(username);
        user.setAvatar(avatar);
        user.setEmail(email);
        user.setBio(bio);
        user.setWebsite(website);
        user.setActive(systemConfigService.selectAllConfig().get("user_need_active").equals("0"));
        userMapper.insert(user);
        if (needActiveEmail) {
            // 发送激活邮件
            new Thread(() -> {
                String title = "感谢注册%s，点击下面链接激活帐号";
                String content = "如果不是你注册了%s，请忽略此邮件&nbsp;&nbsp;<a href='%s/active?email=%s&code=${code}'>点击激活</a>";
                codeService.sendEmail(user.getId(), email, String.format(title, systemConfigService.selectAllConfig().get(
                        "base_url").toString()), String.format(content,
                        systemConfigService.selectAllConfig().get("name").toString(), systemConfigService.selectAllConfig().get(
                                "base_url").toString(), email));
            }).start();
        }
        // 再查一下，有些数据库里默认值保存后，类里还是null
        return this.selectById(user.getId());
    }

    // 递归生成用户名，防止用户名重复
    private String generateUsername() {
        String username = StringUtil.randomString(6);
        if (this.selectByUsername(username) != null) {
            return this.generateUsername();
        }
        return username;
    }

    // 通过手机号登录/注册创建用户
    @Override
    public User addUserWithMobile(String mobile) {
        // 根据手机号查询用户是否注册过
        User user = selectByMobile(mobile);
        if (user == null) {
            String token = this.generateToken();
            String username = generateUsername();
            user = new User();
            user.setUsername(username);
            user.setToken(token);
            user.setInTime(new Date());
            user.setAvatar(identicon.generator(username));
            user.setEmail(null);
            user.setBio(null);
            user.setWebsite(null);
            user.setActive(true);
            userMapper.insert(user);
            // 再查一下，有些数据库里默认值保存后，类里还是null
            return this.selectById(user.getId());
        }
        return user;
    }

    // 根据用户token查询用户
    @Override
    public User selectByToken(String token) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getToken, token);
        return userMapper.selectOne(wrapper);
    }

    // 根据用户mobile查询用户
    @Override
    public User selectByMobile(String mobile) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getMobile, mobile);
        return userMapper.selectOne(wrapper);
    }

    // 根据用户email查询用户
    @Override
    public User selectByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getEmail, email);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    // 查询用户积分榜
    @Override
    public List<User> selectTop(Integer limit) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("score").last("limit " + limit);
        return userMapper.selectList(wrapper);
    }

    // 更新用户信息
    @Override
    public void update(User user) {
        userMapper.updateById(user);
        // 同类调用不走spring管理，无法使用aop编程调用
        SpringContextUtil.getBean(UserService.class).delRedisUser(user);
    }

    // ------------------------------- admin ------------------------------------------

    @Override
    public IPage<User> selectAll(Integer pageNo, String username) {
        MyPage<User> page = new MyPage<>(pageNo, Integer.parseInt((String) systemConfigService.selectAllConfig().get("page_size")));
        page.setDesc("in_time");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            wrapper.lambda().eq(User::getUsername, username);
        }
        return userMapper.selectPage(page, wrapper);
    }

    public User selectByIdNoCatch(Integer id) {
        return userMapper.selectById(id);
    }

    // 查询今天新增的话题数
    @Override
    public int countToday() {
        return userMapper.countToday();
    }

    // 删除用户
    @Override
    public void deleteUser(Integer id) {
        // 删除用户的通知
        notificationService.deleteByUserId(id);
        // 删除用户的收藏
        collectService.deleteByUserId(id);
        // 删除用户发的评论
        commentService.deleteByUserId(id);
        // 删除用户发的帖子
        topicService.deleteByUserId(id);
        // 删除与用户相关的code记录
        codeService.deleteByUserId(id);
        // 删除redis里的缓存
        User user = this.selectById(id);
        SpringContextUtil.getBean(UserService.class).delRedisUser(user);
        // 删除用户本身
        userMapper.deleteById(id);
    }

    // 删除redis缓存
    @Override
    public void delRedisUser(User user) {

    }
}
