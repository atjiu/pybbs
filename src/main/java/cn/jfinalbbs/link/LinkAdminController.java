package cn.jfinalbbs.link;

import cn.jfinalbbs.common.BaseController;
import cn.jfinalbbs.common.Constants;
import cn.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by liuyang on 15/4/26.
 */
@Before(AdminUserInterceptor.class)
public class LinkAdminController extends BaseController {

    public void index() {
        setAttr("admin_links", Link.me.findAll());
        render("index.html");
    }

    public void add() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            render("add.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            Integer maxDisplayIndex = Link.me.maxDisplayIndex();
            if(maxDisplayIndex == null) maxDisplayIndex = 0;
            getModel(Link.class).set("display_index", maxDisplayIndex + 1).save();
            clearCache(Constants.CacheName.LINKLIST, null);
            redirect(Constants.getBaseUrl() + "/admin/link");
        }
    }

    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            setAttr("link", Link.me.findById(id));
            render("edit.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            getModel(Link.class).update();
            clearCache(Constants.CacheName.LINKLIST, null);
            redirect(Constants.getBaseUrl() + "/admin/link");
        }
    }

    public void delete() {
        Integer id = getParaToInt("id");
        if(id == null) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            try {
                Link.me.deleteById(id);
                clearCache(Constants.CacheName.LINKLIST, null);
                success();
            } catch (Exception e) {
                e.printStackTrace();
                error(Constants.DELETE_FAILURE);
            }
        }
    }

    public void sort() {
        Integer[] ids = getParaValuesToInt("ids");
        if(ids != null && ids.length > 0) {
            for(int i = 0; i < ids.length; i++) {
                Link.me.findById(ids[i]).set("display_index", i + 1).update();
            }
            clearCache(Constants.CacheName.LINKLIST, null);
        }
        redirect(Constants.getBaseUrl() + "/admin/link");
    }
}
