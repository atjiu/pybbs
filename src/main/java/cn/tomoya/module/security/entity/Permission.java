package cn.tomoya.module.security.entity;

import cn.tomoya.common.BaseEntity;
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
@Table(name="pybbs_permission")
@Getter
@Setter
public class Permission extends BaseEntity {

    @Id
    @GeneratedValue
    private int id;

    //权限名称
    private String name;

    //权限描述
    private String description;

    //授权链接
    private String url;

    //父节点id
    private int pid;

    /**
     * 角色与权限的关联关系
     * mappedBy: 就是 Role.class 里的 Set<Permission> 的对象名
     */
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
