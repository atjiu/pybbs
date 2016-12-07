package cn.tomoya.common;

import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.util.MarkdownUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
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
        if (StringUtils.isEmpty(content)) return "";
        //处理@
        List<String> users = fetchUsers(content);
        for (String user : users) {
            content = content.replace(user, "[" + user + "](/user/" + user + ")");
        }
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }

    /**
     * 查找一段文本里以 @ 开头的字符串
     *
     * @param str
     * @return
     */
    public static List<String> fetchUsers(String str) {
        List<String> ats = new ArrayList<>();
        String pattern = "@[^\\s]+\\s?";
        Pattern regex = Pattern.compile(pattern);
        Matcher regexMatcher = regex.matcher(str);
        while (regexMatcher.find()) {
            ats.add(regexMatcher.group());
        }
        return ats;
    }

    /**
     * 解析markdown文章(不解析@)
     *
     * @param content
     * @return
     */
    public String markedNotAt(String content) {
        if (StringUtils.isEmpty(content)) return "";
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }
}
