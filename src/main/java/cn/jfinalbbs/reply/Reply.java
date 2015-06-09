package cn.jfinalbbs.reply;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by liuyang on 15/4/2.
 */
public class Reply extends Model<Reply> {

    public static final Reply me = new Reply();

    public List<Reply> findByTid(String tid) {
        return find("select u.avatar, u.nickname, r.* " +
                "from reply r left join user u on r.author_id = u.id " +
                "where r.tid = ? order by r.in_time", tid);
    }

    public int deleteByTid(String tid) {
        return Db.update("delete from reply where tid = ?", tid);
    }

    public int deleteQuoteByRid(String rid) {
        return Db.update("update reply set quote = ?, quote_content = ? where id = ?", 0, "", rid);
    }

    // ------- 后台查询方法 -------
    public Page<Reply> page(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select r.*, t.title ", "from reply r left join topic t on r.tid = t.id order by r.in_time desc");
    }

}
