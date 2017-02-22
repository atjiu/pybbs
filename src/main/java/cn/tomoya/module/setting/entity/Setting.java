package cn.tomoya.module.setting.entity;

import cn.tomoya.common.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_system")
public class Setting extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8923794303434389823L;

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;

    private String description;

    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
