package cn.jfinalbbs.user.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by liuyang on 15/4/2.
 */
public class User extends Model<User> {
    public static final User me = new User();

    public User findByOpenID(String token, String type) {
        String sql = "select u.* from user u where u.token = ? and u.thirdlogin_type = ?";
        return super.findFirst(sql, token, type);
    }

    public List<User> findBySize(int size) {
        return super.find("select u.*, (select count(t.id) from topic t where t.author_id = u.id) as topic_count, " +
                "(select count(r.id) from reply r where r.author_id = u.id) as reply_count " +
                "from user u order by u.score desc limit 0, ?", size);
    }

    // ---------- 后台查询方法 -------
    public Page<User> page(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select * ", "from user order by in_time desc");
    }

}