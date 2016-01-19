package com.jfinalbbs.label;

import com.jfinalbbs.utils.StrUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Label extends Model<Label> {

    public static final Label me = new Label();

    public Page<Label> page(int pageNumber, int pageSize, String name) {
        StringBuffer condition = new StringBuffer();
        if(!StrUtil.isBlank(name)) condition.append(" and l.name like \"%" + name + "%\" ");
        return super.paginate(pageNumber, pageSize, "select l.* ", "from label l where 1 = 1 " + condition + " order by l.topic_count desc, l.in_time desc");
    }

    public List<Label> findByNameLike(String name) {
        return super.find("select * from label where name like ?", "%"+name+"%");
    }

    public Label findByName(String name) {
        return super.findFirst("select * from label where name = ?", name);
    }

    public List<Label> findByTid(String tid) {
        List<Label> labels = new ArrayList<Label>();
        List<LabelTopicId> labelTopicIds = LabelTopicId.me.findByTid(tid);
        for(LabelTopicId lti : labelTopicIds) {
            labels.add(super.findById(lti.getInt("lid")));
        }
        return labels;
    }
}
