package cn.jfinalbbs.mission;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by liuyang on 15/4/2.
 */
public class Mission extends Model<Mission> {
    public static final Mission me = new Mission();

    public Mission findByInTime(String author_id, String startDate, String endDate) {
        return findFirst("select * from mission m " +
                "where m.author_id = ? and (m.in_time between ? and ?)", author_id, startDate, endDate);
    }
}
