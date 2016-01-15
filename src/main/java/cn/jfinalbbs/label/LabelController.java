package cn.jfinalbbs.label;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomoya on 15/10/15.
 */
public class LabelController extends BaseController {

    public void search() {
        String q = getPara("q");
        if(StrUtil.isBlank(q)) {
            renderJson(new ArrayList<String>());
        } else {
            List<Label> labels = Label.me.findByNameLike(q);
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < labels.size(); i++) {
                list.add(labels.get(i).getStr("name"));
            }
            renderJson(list);
        }
    }
}
