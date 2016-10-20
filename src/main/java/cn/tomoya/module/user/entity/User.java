package cn.tomoya.module.user.entity;

import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.security.entity.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_user")
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private int id;

    //用户名
    @Column(unique = true, nullable = false)
    private String username;

    //密码
    @Column(nullable = false)
    private String password;

    //头像
    @Column(nullable = false)
    private String avatar;

    //用户邮箱
    private String email;

    //个人签名
    private String signature;

    //个人主页
    private String url;

    //注册时间
    @Column(nullable = false)
    private Date inTime;

    //用户与角色的关联关系
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="pybbs_user_role",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")}
    )
    private Set<Role> roles = new HashSet<>();

}
