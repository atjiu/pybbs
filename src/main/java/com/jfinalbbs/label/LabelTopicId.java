package com.jfinalbbs.label;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class LabelTopicId extends Model<LabelTopicId> {
    public static final LabelTopicId me = new LabelTopicId();

    public int deleteByLid(int lid) {
        return Db.update("delete from label_topic_id where lid = ?", lid);
    }

    public void deleteByTid(String tid) {
        List<LabelTopicId> labelTopicIds = super.find("select * from label_topic_id where tid = ?", tid);
        if(labelTopicIds.size()>0) {
            for(LabelTopicId lti: labelTopicIds) {
                Db.update("update label set topic_count = (topic_count - 1) where id = ?", lti.get("lid"));
                Db.update("delete from label_topic_id where lid = ? and tid = ?", lti.get("lid"), lti.get("tid"));
            }
        }
    }

    public void save(Integer lid, String tid) {
        LabelTopicId labelTopicId = super.findFirst("select * from label_topic_id where lid = ? and tid = ?", lid, tid);
        if(labelTopicId == null) {
            labelTopicId = new LabelTopicId();
            labelTopicId.set("lid", lid)
                    .set("tid", tid).save();
            //更新标签topic_count
            Label label = Label.me.findById(lid);
            label.set("topic_count", label.getInt("topic_count") + 1).update();
        }
    }

    public List<LabelTopicId> findByTid(String tid) {
        return super.find("select * from label_topic_id where tid = ?", tid);
    }
}
