package cn.jfinalbbs.mission;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.io.Serializable;

/**
 * Created by liuyang on 15/4/2.
 */
public class Mission extends Model<Mission> {
    public static final Mission me = new Mission();

    public Mission findLastByAuthorId(String author_id) {
        return findFirst("select * from mission where author_id = ? order by in_time desc", author_id);
    }

    public Mission findByInTime(String author_id, String startDate, String endDate) {
        return findFirst("select * from mission m " +
                "where m.author_id = ? and (m.in_time between ? and ?)", author_id, startDate, endDate);
    }

    //查询每日签到列表
    public Page<Mission> paginate(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select m.*, u.nickname",
                "from mission m left join user u on m.author_id = u.id order by m.in_time desc");
    }
}
