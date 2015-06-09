package cn.jfinalbbs.system;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

/**
 * Created by liuyang on 15/4/20.
 */
public class Code extends Model<Code> {
    public final static Code me = new Code();

    public Code findByCode(String code, int status, Date date) {
        return super.findFirst("select c.* from code c where c.code = ? and c.status = ? and c.expire_time > ?", code, status, date);
    }

    public int updateByCode(String code) {
        return Db.update("update code c set c.status = 1 where c.code = ?", code);
    }

}
