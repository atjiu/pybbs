package co.yiiu.core.base;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.util.Constants;
import co.yiiu.core.util.DateUtil;
import co.yiiu.core.util.MarkdownUtil;
import co.yiiu.module.node.model.Node;
import co.yiiu.module.node.service.NodeService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class BaseEntity {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private NodeService nodeService;

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

  /**
   * 解析markdown文章
   *
   * @param content
   * @return
   */
  public String marked(String content) {
    if (StringUtils.isEmpty(content))
      return "";
    // 处理@
    List<String> users = fetchUsers(null, content);
    for (String user : users) {
      user = user.trim();
      content = content.replace(user, "[" + user + "](" + siteConfig.getBaseUrl() + "user/" + user.replace("@", "") + ")");
    }
    // markdown 转 html 并返回
    return Jsoup.clean(
        MarkdownUtil.pegDown(content),
        Whitelist
            .relaxed()
            .addAttributes("input", "checked", "type")
            .addAttributes("span", "class")
    );
  }

  /**
   * 解析markdown文章(不解析@)
   *
   * @param content
   * @return
   */
  public String markedNotAt(String content) {
    if (StringUtils.isEmpty(content))
      return "";
    // markdown 转 html 并返回
    return Jsoup.clean(
        MarkdownUtil.pegDown(content),
        Whitelist
            .relaxed()
            .addAttributes("input", "checked", "type")
            .addAttributes("span", "class")
    );
  }

  /**
   * 查找一段文本里以 @ 开头的字符串
   *
   * @param str
   * @return
   */
  public static List<String> fetchUsers(String pattern, String str) {
    List<String> ats = new ArrayList<>();
    if (StringUtils.isEmpty(pattern))
      pattern = "@[^\\s]+\\s?";
    Pattern regex = Pattern.compile(pattern);
    Matcher regexMatcher = regex.matcher(str);
    while (regexMatcher.find()) {
      ats.add(regexMatcher.group());
    }
    return ats;
  }

  public boolean isUp(int userId, String upIds) {
    return upIds != null && upIds.contains(Constants.COMMA + userId + Constants.COMMA);
  }

  public boolean isDown(int userId, String downIds) {
    return downIds != null && downIds.contains(Constants.COMMA + userId + Constants.COMMA);
  }

  /**
   * Did it take more than 5 minutes?
   * over: can't edit own topic
   * not: can edit own topic
   *
   * @param inTime
   * @return
   */
  public boolean overFiveMinute(Date inTime) {
    return DateUtil.isExpire(DateUtil.getMinuteAfter(inTime, 5));
  }
}
