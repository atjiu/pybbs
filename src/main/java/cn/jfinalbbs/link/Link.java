package cn.jfinalbbs.link;

import cn.jfinalbbs.common.Constants;
import com.jfinal.plugin.activerecord.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuyang on 15/4/26.
 */
public class Link extends Model<Link> implements Serializable {
    public static final Link me = new Link();
    private static final long serialVersionUID = 6848091981098837558L;

    public List<Link> findAll() {
        return super.findByCache(
                Constants.CacheName.LINKLIST,
                Constants.CacheKey.LINKLISTKEY,
                "select * from link order by display_index");
    }

    public int maxDisplayIndex() {
        return super.findFirst("select max(display_index) as display_index from link").getInt("display_index");
    }

}
