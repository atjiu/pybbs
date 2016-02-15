package com.jfinalbbs.section;

import com.jfinalbbs.common.BaseModel;
import com.jfinalbbs.common.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Section extends BaseModel<Section> implements Serializable {

    public final static Section me = new Section();

    public List<Section> findAll() {
        return super.findByCache(Constants.SECTIONCACHE, Constants.SECTIONLISTKEY, "select * from jfbbs_section order by display_index");
    }

    public List<Section> findShow() {
        return super.findByCache(Constants.SECTIONCACHE, Constants.SECTIONSHOWLISTKEY, "select * from jfbbs_section where show_status = 1 order by display_index");
    }

    public Section findByTab(String tab) {
        return super.findFirstByCache(Constants.SECTIONCACHE, Constants.SECTIONBYTABKEY + "-" + tab, "select * from jfbbs_section where tab = ?", tab);
    }

    public Section findDefault() {
        List<Section> sections = super.findByCache(Constants.SECTIONCACHE, Constants.DEFAULTSECTIONKEY, "select * from jfbbs_section where default_show = 1");
        if (sections.size() > 0) return sections.get(0);
        return null;
    }

}
