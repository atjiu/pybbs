package cn.tomoya.common;

import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.util.MarkdownUtil;
import com.github.javautils.string.StringUtil;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Log4j
public class BaseEntity {

    @Autowired
    private SiteConfig siteConfig;

    /**
     * 格式化日期
     *
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
     * 解析markdown文章
     *
     * @param content
     * @return
     */
    public String marked(String content) {
        if (StringUtil.isBlank(content)) return "";
        //处理@
        List<String> users = StringUtil.fetchUsers(content);
        for (String user : users) {
            content = content.replace("@" + user, "[@" + user + "](" + siteConfig.getCookieDomain() + "/user/" + user + ")");
        }
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }

    /**
     * 解析markdown文章(不解析@)
     *
     * @param content
     * @return
     */
    public String markedNotAt(String content) {
        if (StringUtil.isBlank(content)) return "";
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }
}
