package cn.tomoya.module.collect;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://bbs.tomoya.cn
 */
public class Collect extends BaseModel<Collect> {

    public static final Collect me = new Collect();

    /**
     * 根据话题id与用户查询收藏记录
     * @param tid
     * @param uid
     * @return
     */
    public Collect findByTidAndUid(Integer tid, Integer uid) {
        return super.findFirstByCache(
                Constants.COLLECT_CACHE,
                Constants.COLLECT_CACHE_KEY + tid + "_" + uid,
                "select * from pybbs_collect where tid = ? and uid = ?",
                tid,
                uid
        );
    }

    /**
     * 查询话题被收藏的数量
     * @param tid
     * @return
     */
    public long countByTid(Integer tid) {
        return super.findFirstByCache(
                Constants.COLLECT_CACHE,
                Constants.COLLECT_CACHE_KEY + tid,
                "select count(1) as count from pybbs_collect where tid = ?",
                tid
        ).getLong("count");
    }

}
