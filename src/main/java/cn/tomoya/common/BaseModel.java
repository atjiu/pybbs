package cn.tomoya.common;

import cn.tomoya.module.user.User;
import com.jfinal.plugin.activerecord.Model;
import cn.tomoya.module.section.Section;
import cn.tomoya.utils.MarkdownUtil;
import cn.tomoya.utils.StrUtil;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class BaseModel<T extends Model> extends Model<T> {

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public String formatDate(Date date) {
        String dateStr = "";
        if (date != null) {
            PrettyTime prettyTime = new PrettyTime(Locale.CHINA);
            dateStr = prettyTime.format(date);
        }
        return dateStr.replace(" ", "");
    }

    /**
     * 根据版块标识查询版块名称
     * @param tab
     * @return
     */
    public String getNameByTab(String tab) {
        Section section = Section.me.findByTab(tab);
        if (section != null) {
            return section.getStr("name");
        }
        return null;
    }

    /**
     * 根据用户昵称查询用户头像
     * @param nickname
     * @return
     */
    public String getAvatarByNickname(String nickname) {
        User user = User.me.findByNickname(nickname);
        if (user != null) {
            return user.getStr("avatar");
        }
        return null;
    }

    /**
     * 解析markdown文章
     * @param content
     * @return
     */
    public String marked(String content) {
        //处理@
        List<String> users = StrUtil.fetchUsers(content);
        for (String user : users) {
            content = content.replace("@" + user, "[@" + user + "](/user/" + user + ")");
        }
        //markdown 转 html 并返回
        return MarkdownUtil.marked(content);
    }

}
