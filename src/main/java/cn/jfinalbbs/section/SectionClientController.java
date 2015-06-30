package cn.jfinalbbs.section;

import cn.jfinalbbs.common.BaseController;

/**
 * Created by liuyang on 2015/6/27.
 */
public class SectionClientController extends BaseController {

    public void index() {
        success(Section.me.findShow());
    }
}
