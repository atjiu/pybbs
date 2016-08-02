package cn.tomoya.module.section;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants.CacheEnum;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class Section extends BaseModel<Section> {

    public static final Section me = new Section();

    public List<Section> findAll() {
        return super.find("select * from pybbs_section");
    }

    public List<Section> findByShowStatus(boolean isshow) {
        Cache cache = Redis.use();
        List list = cache.get(CacheEnum.sections.name() + isshow);
        if(list == null) {
            list = super.find("select * from pybbs_section where show_status = ? order by display_index", isshow);
            cache.set(CacheEnum.sections.name() + isshow, list);
        }
        return list;
    }

    public Section findByTab(String tab) {
        Cache cache = Redis.use();
        Section section = cache.get(CacheEnum.section.name() + tab);
        if(section == null) {
            section = findFirst(
                    "select * from pybbs_section where tab = ?",
                    tab
            );
            cache.set(CacheEnum.section.name() + tab, section);
        }
        return section;
    }

    public String showStatus(Section section) {
        if(section.getBoolean("show_status")) {
            return "true";
        } else {
            return "false";
        }
    }

}
