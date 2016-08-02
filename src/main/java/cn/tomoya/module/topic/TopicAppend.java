package cn.tomoya.module.topic;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants.CacheEnum;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class TopicAppend extends BaseModel<TopicAppend> {

    public static final TopicAppend me = new TopicAppend();

    /**
     * 查询话题追加内容
     * @param tid
     * @return
     */
    public List<TopicAppend> findByTid(Integer tid) {
        Cache cache = Redis.use();
        List list = cache.get(CacheEnum.topicappends.name() + tid);
        if(list == null) {
            list = find(
                    "select * from pybbs_topic_append where isdelete = ? and tid = ? order by in_time",
                    false,
                    tid);
            cache.set(CacheEnum.topicappends.name() + tid, list);
        }
        return list;
    }

    /**
     * 删除话题追加内容
     * @param tid
     */
    public void deleteByTid(Integer tid) {
        Db.update("update pybbs_topic_append set isdelete = 1 where tid = ?", tid);
    }
}
