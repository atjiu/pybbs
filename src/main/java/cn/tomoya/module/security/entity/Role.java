package cn.tomoya.module.security.entity;

import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_role")
@Getter
@Setter
public class Role extends BaseEntity {

    @Id
    @GeneratedValue
    private int id;

    //权限标识
    private String name;

    //权限描述
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    //角色与权限的关联关系
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name="pybbs_role_permission",
            joinColumns={@JoinColumn(name="role_id")},
            inverseJoinColumns={@JoinColumn(name="permission_id")}
    )
    private Set<Permission> permissions = new HashSet<>();

}
