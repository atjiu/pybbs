package cn.jfinalbbs.topic.model;

import cn.jfinalbbs.collect.model.Collect;
import cn.jfinalbbs.reply.model.Reply;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.List;

/**
 * Created by liuyang on 15/4/1.
 */
public class Topic extends Model<Topic> {

    public static final Topic me = new Topic();

    public Page<Topic> paginate(int pageNumber, int pageSize, String tab, String q) {
        String select = "select t.*, (select count(r.id) from reply r where t.id = r.tid) as reply_count, " +
                "(select u.avatar from user u where u.id = t.author_id) as avatar, " +
                "(select u.nickname from user u where u.id = t.author_id) as nickname";
        String orderBy = " order by t.top desc, t.in_time desc ";
        StringBuffer condition = new StringBuffer();
        condition.append("from topic t where 1 = 1 ");
        if(!StrKit.isBlank(tab) && tab.equals("all")) {
            tab = null;
        }
        if(!StrKit.isBlank(tab)) {
            condition.append(" and t.tab = '" + tab + "'");
        }
        if(!StrKit.isBlank(q)) {
            String[] qs = q.split(" ");
            condition.append(" and (");
            for(int c = 0; c < qs.length; c++) {
                condition.append("t.title like '%" + qs[c] + "%' or t.content like '%" + qs[c] + "%' ");
                if(c + 1 < qs.length) condition.append(" or ");
            }
            condition.append(" ) ");
        }
        return super.paginate(pageNumber, pageSize, select, condition + orderBy);
    }

    public Topic findByIdWithUser(String id) {
        List<Topic> topics = find(
                "select t.*, u.nickname, u.avatar, u.score, (select count(r.id) from reply r where r.tid = t.id) as reply_count " +
                        "from topic t left join user u on t.author_id = u.id " +
                        "where t.id = ?", id);
        if(topics.size()>0) {
            Topic topic = topics.get(0);
            topic.set("view", topic.getInt("view") + 1).update();
            //view+1
            return topic;
        }
        return null;
    }

    public List<Topic> findFiveByAuthorId(String authorId) {
        return super.find("select t.*, (select count(r.id) from reply r where r.tid = t.id) as reply_count, " +
                "(select u.avatar from user u where u.id = t.author_id) as avatar from topic t " +
                "where t.author_id = ? order by in_time desc limit 0, 5;", authorId);
    }

    public Page<Topic> paginateByAuthorId(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize,
                "select t.*, (select count(r.id) from reply r where r.tid = t.id) as reply_count,(select u.avatar from user u where u.id = t.author_id) as avatar",
                "from topic t where t.author_id = ? order by in_time desc", authorId);
    }

    // ----- 后台查询方法 --------
    public Page<Topic> page(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select * ", "from topic order by in_time desc");
    }

}
