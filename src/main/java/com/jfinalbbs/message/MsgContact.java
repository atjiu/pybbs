package com.jfinalbbs.message;

import com.jfinalbbs.common.BaseModel;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class MsgContact extends BaseModel<MsgContact> {

    public static final MsgContact me = new MsgContact();

    public MsgContact findByAuthorIdAndToAuthorId(String authorId, String toAuthorId) {
        return super.findFirst("select * from jfbbs_msg_contact where author_id = ? and to_author_id = ?", authorId, toAuthorId);
    }

    /**
     * 根据用户id查询此用户的会话列表
     * @param authorId
     * @return
     */
    public List<MsgContact> findByAuthorId(String authorId) {
        return super.find("select mc.id, mc.author_id, mc.to_author_id, mc.msg_count, " +
                "left(mc.last_msg_content, 50) as last_msg_content, mc.in_time, u.avatar, u.nickname " +
                "from jfbbs_msg_contact mc left join jfbbs_user u on mc.to_author_id = u.id " +
                "where mc.author_id = ? and mc.is_delete = 0 order by mc.last_msg_time desc", authorId);
    }

}
