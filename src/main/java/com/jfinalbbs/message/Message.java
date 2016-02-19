package com.jfinalbbs.message;

import com.jfinalbbs.common.BaseModel;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Message extends BaseModel<Message> {

    public static final Message me = new Message();

    /**
     * 根据会话id查询会话消息内容
     *
     * @param contactId
     * @return
     */
    public List<Message> findByContactId(Integer contactId) {
        return super.find("select m.*, u.avatar, u.nickname from jfbbs_message m " +
                "left join jfbbs_user u on m.author_id = u.id " +
                "left join jfbbs_msg_contact mc on m.contact_id = mc.id " +
                "where contact_id = ? and mc.is_delete = 0 order by in_time desc", contactId);
    }

}
