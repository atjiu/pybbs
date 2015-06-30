package cn.jfinalbbs.index;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.topic.Topic;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by Tomoya on 15/6/9.
 */
public class IndexClientController extends BaseController {

    public void index() {
        String tab = getPara("tab");
        String q = getPara("q");
        if(tab == null) tab = "all";
        Page<Topic> page = Topic.me.paginate(getParaToInt("p", 1), getParaToInt("size", 20), tab, q, 1);
        success(page);
    }
}
