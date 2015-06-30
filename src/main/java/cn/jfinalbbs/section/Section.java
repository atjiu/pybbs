package cn.jfinalbbs.section;

import cn.jfinalbbs.common.Constants;
import com.jfinal.plugin.activerecord.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuyang on 15/4/25.
 */
public class Section extends Model<Section> implements Serializable {

    private static final long serialVersionUID = 4351698554467528103L;

    public final static Section me = new Section();

    public List<Section> findAll() {
        return super.findByCache(Constants.CacheName.SECTIONLIST, Constants.CacheKey.SECTIONLISTKEY, "select * from section order by display_index");
    }

    public List<Section> findShow() {
        return super.findByCache(Constants.CacheName.SECTIONSHOWLIST, Constants.CacheKey.SECTIONSHOWLISTKEY, "select * from section where show_status = 1 order by display_index");
    }

}
