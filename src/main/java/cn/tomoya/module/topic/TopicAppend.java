package cn.tomoya.module.topic;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class TopicAppend extends BaseModel<TopicAppend> {

    public static final TopicAppend me = new TopicAppend();

    /**
     * 查询话题追加内容
     * @param tid
     * @return
     */
    public List<TopicAppend> findByTid(Integer tid) {
        return super.findByCache(
                Constants.TOPIC_APPEND_CACHE,
                Constants.TOPIC_APPEND_CACHE_KEY + tid,
                "select * from pybbs_topic_append where isdelete = ? and tid = ? order by in_time",
                false,
                tid
        );
    }

    /**
     * 删除话题追加内容
     * @param tid
     */
    public void deleteByTid(Integer tid) {
        Db.update("update pybbs_topic_append set isdelete = 1 where tid = ?", tid);
    }
}
