package cn.jfinalbbs.user;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by liuyang on 15/4/9.
 */
public class AdminUser extends Model<AdminUser> {

    public final static AdminUser me = new AdminUser();

    //根据用户名，密码登录
    public AdminUser login(String username, String password) {
        return super.findFirst("select * from admin_user where username = ? and password = ?", username, password);
    }
}
