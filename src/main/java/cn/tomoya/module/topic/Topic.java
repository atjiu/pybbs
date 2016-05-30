package cn.tomoya.module.topic;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Topic extends BaseModel<Topic> {

    public static final Topic me = new Topic();

    /**
     * 根据tab分页查询话题列表
     *
     * @param pageNumber
     * @param pageSize
     * @param tab
     * @return
     */
    public Page<Topic> page(Integer pageNumber, Integer pageSize, String tab) {
        if (tab.equals("all")) {
            return pageAll(pageNumber, pageSize);
        } else if (tab.equals("good")) {
            return pageGood(pageNumber, pageSize);
        } else if (tab.equals("noreply")) {
            return pageNoReply(pageNumber, pageSize);
        } else {
            return super.paginate(
                    pageNumber,
                    pageSize,
                    "select t.* ",
                    "from pybbs_topic t where t.isdelete = ? and t.tab = ? order by t.top desc, t.last_reply_time desc",
                    false,
                    tab
            );
        }
    }

    /**
     * 分页查询所有话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<Topic> pageAll(Integer pageNumber, Integer pageSize) {
        return super.paginate(
                pageNumber,
                pageSize,
                "select t.* ",
                "from pybbs_topic t where t.isdelete = ? order by t.top desc, t.last_reply_time desc",
                false
        );
    }

    /**
     * 分页查询精华话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<Topic> pageGood(Integer pageNumber, Integer pageSize) {
        return super.paginate(
                pageNumber,
                pageSize,
                "select t.* ",
                "from pybbs_topic t where t.isdelete = ? and t.good = ? order by t.top desc, t.last_reply_time desc",
                false,
                true
        );
    }

    /**
     * 分页查询无人回复话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<Topic> pageNoReply(Integer pageNumber, Integer pageSize) {
        return super.paginate(
                pageNumber,
                pageSize,
                "select t.* ",
                "from pybbs_topic t where t.isdelete = ? and t.id not in (select r.tid from pybbs_reply r) order by t.top desc, t.last_reply_time desc",
                false
        );
    }

    /**
     * 根据id查询话题内容并缓存
     *
     * @param id
     * @return
     */
    public Topic findById(Integer id) {
        return super.findFirstByCache(Constants.TOPIC_CACHE,
                Constants.TOPIC_CACHE_KEY + id,
                "select * from pybbs_topic where id = ?",
                id
        );
    }

    /**
     * 查询当前作者其他话题
     * @param currentTopicId
     * @param author
     * @param limit
     * @return
     */
    public List<Topic> findOtherTopicByAuthor(Integer currentTopicId, String author, Integer limit) {
        return super.find(
                "select * from pybbs_topic where isdelete = ? and id <> ? and author = ? order by in_time desc limit ?",
                false,
                currentTopicId,
                author,
                limit
        );
    }

    /**
     * 查询用户
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    public Page<Topic> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        return super.paginateByCache(
                Constants.USER_TOPICS_CACHE,
                Constants.USER_TOPICS_CACHE_KEY + author + pageNumber + pageSize,
                pageNumber,
                pageSize,
                "select * ",
                "from pybbs_topic where author = ? order by in_time desc",
                author
        );
    }

    /**
     * 删除话题
     * @param id
     */
    public void deleteById(Integer id) {
        Db.update("update pybbs_topic set isdelete = 1 where id = ?", id);
    }

    /**
     * 话题置顶
     * @param id
     */
    public void top(Integer id) {
        Topic topic = findById(id);
        topic.set("top", !topic.getBoolean("top")).update();
    }

    /**
     * 话题加精
     * @param id
     */
    public void good(Integer id) {
        Topic topic = findById(id);
        topic.set("good", !topic.getBoolean("good")).update();
    }

}
