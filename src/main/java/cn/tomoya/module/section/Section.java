package cn.tomoya.module.section;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Section extends BaseModel<Section> {

    public static final Section me = new Section();

    public List<Section> findAll() {
        return super.findByCache(
                Constants.SECTIONS_CACHE,
                Constants.SECTIONS_CACHE_KEY,
                "select * from pybbs_section where show_status = ? order by display_index",
                true
        );
    }

    public Section findByTab(String tab) {
        return super.findFirstByCache(
                Constants.SECTION_CACHE,
                Constants.SECTION_CACHE_KEY + tab,
                "select * from pybbs_section where tab = ?",
                tab
        );
    }

}
