package com.jfinalbbs.label;

import com.jfinal.plugin.activerecord.Page;
import com.jfinalbbs.common.BaseModel;
import com.jfinalbbs.topic.Topic;
import com.jfinalbbs.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Label extends BaseModel<Label> {

    public static final Label me = new Label();

    public Page<Label> page(int pageNumber, int pageSize, String name) {
        StringBuffer condition = new StringBuffer();
        if (!StrUtil.isBlank(name)) condition.append(" and l.name like \"%" + name + "%\" ");
        return super.paginate(pageNumber, pageSize, "select l.* ", "from jfbbs_label l where 1 = 1 " + condition + " order by l.topic_count desc, l.in_time desc");
    }

    public List<Label> findByNameLike(String name) {
        return super.find("select * from jfbbs_label where name like ?", "%" + name + "%");
    }

    public Label findByName(String name) {
        return super.findFirst("select * from jfbbs_label where name = ?", name);
    }

    public List<Label> findByTid(String tid) {
        List<Label> labels = new ArrayList<Label>();
        List<LabelTopicId> labelTopicIds = LabelTopicId.me.findByTid(tid);
        for (LabelTopicId lti : labelTopicIds) {
            labels.add(super.findById(lti.getInt("lid")));
        }
        return labels;
    }

    //根据标签查询同类的话题
    public List<Topic> findByLabels(String id, List<Label> labels, int limit) {
        String labels_str = "";
        List<Topic> topics = new ArrayList<Topic>();
        for (Label label : labels) {
            labels_str += label.get("id") + ",";
        }
        labels_str = labels_str.substring(0, labels_str.length() - 1);
        List<LabelTopicId> labelTopicIds = LabelTopicId.me.find(
                "select * from jfbbs_label_topic_id where lid in ("+labels_str+");");
        if(labelTopicIds.size() > 0) {
            String topics_id_str = "";
            for(LabelTopicId labelTopicId: labelTopicIds) {
                topics_id_str += "'" + labelTopicId.getStr("tid") + "',";
            }
            topics_id_str = topics_id_str.substring(0, topics_id_str.length() - 1);
//            topics = Topic.me.find(
//                    "select * from jfbbs_topic where `id` in ( ? ) limit ?;", topics_id_str, limit);
            topics = Topic.me.find("select * from jfbbs_topic where id in (" + topics_id_str + ") and id <> ? limit ?", id, limit);
        }
        return topics;
    }

    public List<Label> findAll() {
        return super.find("select * from jfbbs_label order by topic_count desc, in_time desc");
    }
}
