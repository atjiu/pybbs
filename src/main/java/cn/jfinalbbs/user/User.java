package cn.jfinalbbs.user;

import cn.jfinalbbs.utils.StrUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by liuyang on 15/4/2.
 */
public class User extends Model<User> {
    public static final User me = new User();

    public User findByOpenID(String openId, String type) {
        String sql = "select u.* from user u where u.open_id = ? and u.thirdlogin_type = ?";
        return super.findFirst(sql, openId, type);
    }

    public User findByToken(String token) {
        return super.findFirst("select u.* from user u where u.token = ?", token);
    }

    public List<User> findBySize(int size) {
        return super.find("select u.*, (select count(t.id) from topic t where t.author_id = u.id) as topic_count, " +
                "(select count(r.id) from reply r where r.author_id = u.id) as reply_count " +
                "from user u order by u.score desc limit 0, ?", size);
    }

    public User localLogin(String email, String password) {
        return super.findFirst("select * from user where email = ? and password = ?", email, password);
    }

    public User findByEmail(String email) {
        return super.findFirst("select * from user where email = ?", email);
    }

    public int updateByEmail(String email, String newPass) {
        return Db.update("update user u set u.password = ?, u.token = ? where u.email = ?", newPass, StrUtil.getUUID(), email);
    }

    // ---------- 后台查询方法 -------
    public Page<User> page(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select * ", "from user order by in_time desc");
    }

    public List<User> list() {
        return super.find("select * from user");
    }

}