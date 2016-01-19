package com.jfinalbbs.section;

import com.jfinalbbs.common.Constants;
import com.jfinal.plugin.activerecord.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Section extends Model<Section> implements Serializable {

    private static final long serialVersionUID = 4351698554467528103L;

    public final static Section me = new Section();

    public List<Section> findAll() {
        return super.findByCache(Constants.CacheName.SECTIONLIST, Constants.CacheKey.SECTIONLISTKEY, "select * from section order by display_index");
    }

    public List<Section> findShow() {
        return super.findByCache(Constants.CacheName.SECTIONSHOWLIST, Constants.CacheKey.SECTIONSHOWLISTKEY, "select * from section where show_status = 1 order by display_index");
    }

    public Section findByTab(String tab) {
        return super.findFirst("select * from section where tab = ?", tab);
    }

    public Section findDefault() {
        List<Section> sections = super.findByCache(Constants.CacheName.DEFAULTSECTION, Constants.CacheKey.DEFAULTSECTIONKEY, "select * from section where default_show = 1");
        if(sections.size() > 0) return sections.get(0);
        return null;
    }

}
