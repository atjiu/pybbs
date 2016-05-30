package cn.tomoya.module.reply;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Reply extends BaseModel<Reply> {

    public static final Reply me = new Reply();
    static {
        PropKit.use("config.properties");
    }

    /**
     * 根据话题id查询回复数量
     * @param tid
     * @return
     */
    public int findCountByTid(Integer tid) {
        return super.find("select id from pybbs_reply where tid = ?", tid).size();
    }

    /**
     * 分页查询话题的回复列表
     * @param pageNumber
     * @param pageSize
     * @param tid
     * @return
     */
    public Page<Reply> page(Integer pageNumber, Integer pageSize, Integer tid) {
        int count = this.findCountByTid(tid);
        pageNumber = pageNumber == null ? (int)(count / PropKit.getInt("replyPageSize")) + 1 : pageNumber;
        System.out.println(pageNumber);
        return super.paginate(
                pageNumber,
                pageSize,
                "select * ", "from pybbs_reply where isdelete = ? and tid = ?",
                false,
                tid
        );
    }

    /**
     * 分页查询回复列表
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    public Page<Reply> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        return super.paginateByCache(
                Constants.USER_REPLIES_CACHE,
                Constants.USER_REPLIES_CACHE_KEY + author + pageNumber + pageSize,
                pageNumber,
                pageSize,
                "select t.title, t.author as topicAuthor, t.in_time, r.tid, r.content ",
                "from pybbs_topic t, pybbs_reply r where t.id = r.tid and r.author = ? order by r.in_time desc",
                author
        );
    }

    /**
     * 删除话题回复内容
     * @param tid
     */
    public void deleteByTid(Integer tid) {
        Db.update("update pybbs_reply set isdelete = 1 where tid = ?", tid);
    }
}
