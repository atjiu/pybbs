package cn.tomoya.module.user;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants.CacheEnum;
import cn.tomoya.module.system.UserRole;
import cn.tomoya.utils.StrUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class User extends BaseModel<User> {
    public static final User me = new User();

    /**
     * 根据Github_access_token查询用户信息
     * @param thirdId
     * @return
     */
    public User findByThirdId(String thirdId) {
        return super.findFirst("select * from pybbs_user where third_id = ?", thirdId);
    }

    /**
     * 更新access_token查询并缓存用户信息
     * @param accessToken
     * @return
     */
    public User findByAccessToken(String accessToken) {
        Cache cache = Redis.use();
        User user = cache.get(CacheEnum.useraccesstoken.name() + accessToken);
        if(user == null) {
            user = findFirst(
                    "select * from pybbs_user where access_token = ? and expire_time > ?",
                    accessToken,
                    new Date());
            cache.set(CacheEnum.useraccesstoken.name() + accessToken, user);
        }
        return user;
    }

    /**
     * 根据昵称查询并缓存用户信息
     * @param nickname
     * @return
     */
    public User findByNickname(String nickname) throws UnsupportedEncodingException {
        if(StrUtil.isBlank(nickname)) {
            return null;
        }
        Cache cache = Redis.use();
        User user = cache.get(CacheEnum.usernickname.name() + URLEncoder.encode(nickname, "utf-8"));
        if(user == null) {
            user = findFirst(
                    "select * from pybbs_user where nickname = ?",
                    URLDecoder.decode(nickname, "utf-8")
            );
            cache.set(CacheEnum.usernickname.name() + URLEncoder.encode(nickname, "utf-8"), user);
        }
        return user;
    }

    /**
     * 分页查询所有用户，倒序
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<User> page(Integer pageNumber, Integer pageSize) {
        return super.paginate(pageNumber, pageSize, "select * ", "from pybbs_user order by in_time desc");
    }

    /**
     * 根据昵称删除用户
     * @param nickname
     */
    public void deleteByNickname(String nickname) {
        Db.update("delete from pybbs_user where nickname = ?", nickname);
    }

    /**
     * 用户勾选角色关联处理
     * @param userId
     * @param roles
     */
    public void correlationRole(Integer userId, Integer[] roles) {
        //先删除已经存在的关联
        Db.update("delete from pybbs_user_role where uid = ?", userId);
        //建立新的关联关系
        if(roles != null) {
            for (Integer rid : roles) {
                UserRole userRole = new UserRole();
                userRole.set("uid", userId)
                        .set("rid", rid)
                        .save();
            }
        }
    }

    /**
     * 根据权限id查询拥有这个权限的用户列表
     * @param id
     * @return
     */
    public List<User> findByPermissionId(Integer id) {
        return super.find("select u.* from pybbs_user u, pybbs_user_role ur, pybbs_role r, pybbs_role_permission rp, " +
                "pybbs_permission p where u.id = ur.uid and ur.rid = r.id and r.id = rp.rid and rp.pid = p.id and p.id = ?",
                id);
    }

    /**
     * 积分榜用户
     * @return
     */
    public List<User> scores(Integer limit) {
        return super.find(
                "select user.*, role.description from pybbs_user user, pybbs_role role, pybbs_user_role ur " +
                        "where user.id = ur.uid and ur.rid = role.id order by score desc, in_time desc limit ?",
                limit
        );
    }

}