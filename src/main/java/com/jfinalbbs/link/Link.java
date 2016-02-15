package com.jfinalbbs.link;

import com.jfinalbbs.common.BaseModel;
import com.jfinalbbs.common.Constants;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Link extends BaseModel<Link> {
    public static final Link me = new Link();

    public List<Link> findAll() {
        return super.findByCache(
                Constants.LINKCACHE,
                Constants.LINKLISTKEY,
                "select * from jfbbs_link order by display_index");
    }

    public Integer maxDisplayIndex() {
        return super.findFirst("select max(display_index) as display_index from jfbbs_link").getInt("display_index");
    }

}
