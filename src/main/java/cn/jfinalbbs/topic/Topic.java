package cn.jfinalbbs.topic;

import cn.jfinalbbs.section.Section;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by liuyang on 15/4/1.
 */
public class Topic extends Model<Topic> {

    public static final Topic me = new Topic();

    // --------------- 前台查询方法 开始 --------------
    public Page<Topic> paginate(int pageNumber, int pageSize, String tab, String q, Integer show_status) {
        String select = "select s.tab, s.name as sectionName, t.*, (select count(r.id) from reply r where t.id = r.tid) as reply_count, " +
                "(select u.avatar from user u where u.id = t.author_id) as avatar, " +
                "(select u.nickname from user u where u.id = t.author_id) as nickname";
        String orderBy = " order by t.top desc, t.in_time desc ";
        StringBuffer condition = new StringBuffer();
        condition.append("from topic t left join section s on t.s_id = s.id where 1 = 1 ");
        if(show_status != null) {
            condition.append(" and t.show_status = " + show_status);
        }
        if(!StrKit.isBlank(tab) && tab.equals("all")) {
            tab = null;
        }
        if(!StrKit.isBlank(tab)) {
            condition.append(" and s.tab = '" + tab + "'");
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
        List<Section> sections = Section.me.findShow();
        if(sections.size()>0) {
            String sid = "";
            for(Section s: sections) {
                sid += s.get("id") + ",";
            }
            condition.append(" and t.s_id in ("+sid.substring(0, sid.length() - 1)+") ");
        }
        return super.paginate(pageNumber, pageSize, select, condition + orderBy);
    }

    public Topic findByIdWithUser(String id) {
        List<Topic> topics = find(
                "select t.*, s.name as sectionName, s.tab, u.nickname, u.avatar, u.score, u.signature, " +
                        "(select count(r.id) from reply r where r.tid = t.id) as reply_count " +
                        " from topic t left join user u on t.author_id = u.id " +
                        " left join section s on s.id = t.s_id " +
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
        return super.find("select t.*, s.tab, s.name as sectionName, (select count(r.id) from reply r where r.tid = t.id) as reply_count, " +
                "(select u.avatar from user u where u.id = t.author_id) as avatar from topic t left join section s on t.s_id = s.id " +
                "where t.author_id = ? order by in_time desc limit 0, 5;", authorId);
    }

    public Page<Topic> paginateByAuthorId(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize,
                "select t.*, (select s.tab from section s where s.id = t.s_id) as tab, (select s.name from section s where s.id = t.s_id) as sectionName, (select count(r.id) from reply r where r.tid = t.id) as reply_count,(select u.avatar from user u where u.id = t.author_id) as avatar",
                "from topic t where t.author_id = ? order by in_time desc", authorId);
    }

    public Topic findWithSection(String id) {
        return super.findFirst("select t.*, s.name as sectionName, s.tab from topic t left join section s on t.s_id = s.id where t.id = ?", id);
    }
    // --------------- 前台查询方法 结束 --------------


    // --------------- 后台查询方法 开始 --------------
    public Page<Topic> page(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select t.*, s.name as sectionName, s.tab, u.nickname ", "from topic t left join section s on t.s_id = s.id left join user u on t.author_id = u.id order by t.top desc, t.in_time desc");
    }
    // --------------- 后台查询方法 结束 --------------

}
