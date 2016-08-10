package cn.tomoya.module.reply;

import cn.tomoya.common.BaseModel;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class Reply extends BaseModel<Reply> {

    public static final Reply me = new Reply();

    /**
     * 根据话题id查询回复数量
     * @param tid
     * @return
     */
    public int findCountByTid(Integer tid) {
        return super.find("select id from pybbs_reply where isdelete = ? and tid = ?", false, tid).size();
    }

    /**
     * 分页查询全部话题
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<Reply> findAll(Integer pageNumber, Integer pageSize) {
        return super.paginate(
                pageNumber,
                pageSize,
                "select r.author as replyAuthor, r.content, r.in_time, t.id as tid, t.author as topicAuthor, t.title ",
                "from pybbs_reply r left join pybbs_topic t on r.tid = t.id order by r.in_time desc"
            );
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
        pageNumber = pageNumber == null ? (count / PropKit.getInt("replyPageSize")) + 1 : pageNumber;
        return super.paginate(
                pageNumber,
                pageSize,
                "select * ", "from pybbs_reply where isdelete = ? and tid = ?",
                false,
                tid
        );
    }

    /**
     * 根据话题id查询回复列表
     * @param topicId
     * @return
     */
    public List<Reply> findByTopicId(Integer topicId) {
        return super.find("select * from pybbs_reply where isdelete = ? and tid = ?", false, topicId);
    }

    /**
     * 分页查询回复列表
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    public Page<Reply> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        return super.paginate(
                pageNumber,
                pageSize,
                "select t.title, t.author as topicAuthor, t.in_time, r.tid, r.content ",
                "from pybbs_topic t, pybbs_reply r where t.isdelete = ? and r.isdelete = ? and t.id = r.tid and r.author = ? order by r.in_time desc",
                false,
                false,
                author
        );
    }

    /**
     * 查询用户回复的话题列表
     * @param author
     * @return
     */
    public List<Reply> findByAuthor(String author) {
        return find("select t.*, r.content as replyContent, r.author as replyAuthor " +
                " from pybbs_topic t, pybbs_reply r " +
                "where t.isdelete = ? and r.isdelete = ? and t.id = r.tid and r.author = ? order by r.in_time desc", false, false, author);
    }

    /**
     * 删除话题回复内容
     * @param tid
     */
    public void deleteByTid(Integer tid) {
        Db.update("update pybbs_reply set isdelete = ? where tid = ?", true, tid);
    }

    public void deleteById(Integer id) {
        Db.update("update pybbs_reply set isdelete = ? where id = ?", true, id);
    }
}
