package cn.tomoya.module.section;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.CacheEnum;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://bbs.tomoya.cn
 */
public class Section extends BaseModel<Section> {

    public static final Section me = new Section();

    public List<Section> findAll() {
        Cache cache = Redis.use();
        List list = cache.get(CacheEnum.sections);
        if(list == null) {
            list = find("select * from pybbs_section where show_status = ? order by display_index", true);
            cache.set(CacheEnum.sections, list);
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

}
