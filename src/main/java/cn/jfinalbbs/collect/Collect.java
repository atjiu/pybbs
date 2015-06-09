package cn.jfinalbbs.collect;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by liuyang on 15/4/4.
 */
public class Collect extends Model<Collect> {

    public static final Collect me = new Collect();

    public Page<Collect> findByAuthorId(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize, "select * ", "from collect where author_id = ?", authorId);
    }

    public Collect findByTidAndAuthorId(String tid, String authorId) {
        return super.findFirst("select * from collect where tid = ? and author_id = ?", tid, authorId);
    }

    public boolean deleteByTidAndAuthorId(String tid, String authorId) {
        Collect collect = findByTidAndAuthorId(tid, authorId);
        return super.deleteById(collect.get("id"));
    }

    public Page<Collect> findByAuthorIdWithTopic(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize, " select t.*, s.name as sectionName, s.tab, u.avatar, " +
                        " (select count(r.id) from reply r where r.tid = t.id) as reply_count ",
                        " from collect c left join topic t on c.tid = t.id " +
                        " left join section s on s.id = t.s_id " +
                        " left join user u on u.id = t.author_id " +
                        " where c.author_id = ?", authorId);
    }

    public int deleteByTid(String tid) {
        return Db.update("delete from collect where tid = ?", tid);
    }

}
