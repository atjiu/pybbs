package co.yiiu.pybbs.util;

import co.yiiu.pybbs.service.ISystemConfigService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class BaseModel {

    private Logger log = LoggerFactory.getLogger(BaseModel.class);

    @Autowired
    private ISystemConfigService systemConfigService;

    private static final long MINUTE = 60 * 1000;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long WEEK = 7 * DAY;
    private static final long MONTH = 31 * DAY;
    private static final long YEAR = 12 * MONTH;

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public String formatDate(Date date) {
        if (date == null) return "";

        long offset = System.currentTimeMillis() - date.getTime();
        if (offset > YEAR) {
            return (offset / YEAR) + "年前";
        } else if (offset > MONTH) {
            return (offset / MONTH) + "个月前";
        } else if (offset > WEEK) {
            return (offset / WEEK) + "周前";
        } else if (offset > DAY) {
            return (offset / DAY) + "天前";
        } else if (offset > HOUR) {
            return (offset / HOUR) + "小时前";
        } else if (offset > MINUTE) {
            return (offset / MINUTE) + "分钟前";
        } else {
            return "刚刚";
        }
    }

    public String formatContent(String content) {
        if (StringUtils.isEmpty(content)) return "";
        List<String> atUsers = StringUtil.fetchAtUser(content);
        if (!atUsers.isEmpty()) {
            for (String atUser : atUsers) {
                content = content.replace(atUser, "[" + atUser + "](" + systemConfigService.selectAllConfig().get("base_url")
                        .toString() + "/user/" + atUser.replace("@", "") + ")");
            }
        }
        content = MarkdownUtil.render(content);
        // 先对内容进行过滤
        content = SensitiveWordUtil.replaceSensitiveWord(content, "*", SensitiveWordUtil.MinMatchType);
        // 解析内容里的视频链接
        content = Jsoup.clean(content, Whitelist.relaxed().addTags("code", "pre", "video", "source")
                .addAttributes("code", "class")
                .addAttributes("video", "class", "controls")
                .addAttributes("source", "src", "type")
        );
        Document parse = Jsoup.parse(content, "", Parser.xmlParser());
        Elements tableElements = parse.select("table");
        tableElements.forEach(element -> element.addClass("table table-bordered"));
        Elements aElements = parse.select("p");
        if (!aElements.isEmpty()) {
            aElements.forEach(element -> {
                try {
                    String href = element.text();
                    if (href.contains("//www.youtube.com/watch")) {
                        URL aUrl = new URL(href);
                        String query = aUrl.getQuery();
                        Map<String, Object> querys = StringUtil.formatParams(query);
                        element.text("");
                        element.addClass("embed-responsive embed-responsive-16by9");
                        element.append("<iframe class='embedded_video' src='https://www.youtube.com/embed/" + querys.get("v") +
                                "' frameborder='0' allowfullscreen></iframe>");
                    } else if (href.contains("//v.youku.com/v_show/")) {
                        element.text("");
                        URL aUrl = new URL(href);
                        String _href = "https://player.youku.com/embed/" + aUrl.getPath().replace("/v_show/id_", "").replace("" +
                                ".html", "");
                        element.addClass("embed-responsive embed-responsive-16by9");
                        element.append("<iframe class='embedded_video' src='" + _href + "' frameborder='0' " +
                                "allowfullscreen></iframe>");
                    } else if (href.contains("//www.bilibili.com/video/")) {
                        element.text("");
                        URL aUrl = new URL(href);
                        String _href = "//player.bilibili.com/player.html?aid=" + aUrl.getPath().replace("/video/av", "");
                        element.addClass("embed-responsive embed-responsive-16by9");
                        element.append("<iframe class='embedded_video' src='" + _href + "' frameborder='0' " +
                                "allowfullscreen></iframe>");
                    } else if (href.contains("//v.qq.com/x/cover/")) {
                        element.text("");
                        URL aUrl = new URL(href);
                        String _href = "https://v.qq.com/txp/iframe/player.html?vid=" + aUrl.getPath().substring(aUrl.getPath().lastIndexOf("/") + 1).replace(".html", "");
                        element.addClass("embed-responsive embed-responsive-16by9");
                        element.append("<iframe class='embedded_video' src='" + _href + "' frameborder='0' " +
                                "allowfullscreen></iframe>");
                    }
                } catch (MalformedURLException e) {
                    log.error(e.getMessage());
                }
            });
        }
        return parse.outerHtml();
    }

    // 将用户点赞的id从字符串转成集合
    public Set<String> getUpIds(String upIds) {
        if (StringUtils.isEmpty(upIds)) return new HashSet<>();
        return StringUtils.commaDelimitedListToSet(upIds);
    }

    public boolean isEmpty(String txt) {
        return StringUtils.isEmpty(txt);
    }
}
