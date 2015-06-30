package cn.jfinalbbs.section;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by liuyang on 15/4/25.
 */
@Before(AdminUserInterceptor.class)
public class SectionAdminController extends BaseController {

    // 查询模块列表
    public void index() {
        setAttr("admin_sections", Section.me.findAll());
        render("index.html");
    }

    // 添加模块
    public void add() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            render("add.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String name = getPara("name");
            Integer show_status = getParaToInt("show_status");
            String tab = getPara("tab");
            Section section = new Section();
            section.set("name", name).set("show_status", show_status).set("tab", tab).set("display_index", 99).save();
            // clear cache
            clearCache(Constants.CacheName.SECTIONLIST, null);
            clearCache(Constants.CacheName.SECTIONSHOWLIST, null);
            redirect(Constants.getBaseUrl() + "/admin/section/index");
        }
    }

    // 编辑模块
    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            setAttr("section", Section.me.findById(id));
            render("edit.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String name = getPara("name");
            Integer show_status = getParaToInt("show_status");
            String tab = getPara("tab");
            Section section = Section.me.findById(id);
            section.set("name", name).set("show_status", show_status).set("tab", tab).update();
            // clear cache
            clearCache(Constants.CacheName.SECTIONLIST, null);
            clearCache(Constants.CacheName.SECTIONSHOWLIST, null);
            redirect(Constants.getBaseUrl() + "/admin/section/index");
        }
    }

    // 排序
    public void sort() {
        Integer[] ids = getParaValuesToInt("ids");
        if(ids != null && ids.length > 0) {
            for(int i = 0; i < ids.length; i++) {
                Section section = Section.me.findById(ids[i]);
                section.set("display_index", i+1).update();
            }
        }
        // clear cache
        clearCache(Constants.CacheName.SECTIONLIST, null);
        redirect(Constants.getBaseUrl() + "/admin/section/index");
    }

    // 删除模块
    public void delete() {
        Integer id = getParaToInt("id");
        if(id == null) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            try {
                Section.me.deleteById(id);
                clearCache(Constants.CacheName.SECTIONLIST, null);
                success();
            } catch (Exception e) {
                e.printStackTrace();
                error(Constants.DELETE_FAILURE);
            }
        }
    }
}
