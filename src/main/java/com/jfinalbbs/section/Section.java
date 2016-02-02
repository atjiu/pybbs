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

    public final static Section me = new Section();

    public List<Section> findAll() {
        return super.findByCache(Constants.SECTIONLIST, Constants.SECTIONLISTKEY, "select * from jfbbs_section order by display_index");
    }

    public List<Section> findShow() {
        return super.findByCache(Constants.SECTIONSHOWLIST, Constants.SECTIONSHOWLISTKEY, "select * from jfbbs_section where show_status = 1 order by display_index");
    }

    public Section findByTab(String tab) {
        return super.findFirst("select * from jfbbs_section where tab = ?", tab);
    }

    public Section findDefault() {
        List<Section> sections = super.findByCache(Constants.DEFAULTSECTION, Constants.DEFAULTSECTIONKEY, "select * from jfbbs_section where default_show = 1");
        if(sections.size() > 0) return sections.get(0);
        return null;
    }

}
