package co.yiiu.pybbs.util;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class MarkdownUtil {

  public static String render(String content) {
    List<Extension> extensions = Arrays.asList(
        AutolinkExtension.create(),
        TablesExtension.create());

    Parser parser = Parser.builder()
        .extensions(extensions)
        .build();
    // 回车一次就可以实现换行
    HtmlRenderer renderer = HtmlRenderer.builder()
        .softbreak("<br/>")
        .attributeProviderFactory(context -> new MyAttributeProvider())
        .extensions(extensions)
        .build();
    Node document = parser.parse(content == null ? "" : content);
    return renderer.render(document);
  }

  static class MyAttributeProvider implements AttributeProvider {

    @Override
    public void setAttributes(Node node, String s, Map<String, String> map) {
      // 给图片添加上类样式，类样式可以在css里自定义
      if (node instanceof Image) {
        map.put("class", "md-image");
      }
      // 给表格添加上类样式，类样式可以在css里自定义
      if (node instanceof TableBlock) {
        map.put("class", "table table-bordered");
      }
    }
  }
}
