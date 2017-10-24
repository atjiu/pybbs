package co.yiiu.core.util;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class MarkdownUtil {

  public static String render(String content) {
    List<Extension> extensions = Arrays.asList(
        AutolinkExtension.create(),
        StrikethroughExtension.create(),
        TablesExtension.create());

    Parser parser = Parser.builder()
        .extensions(extensions)
        .build();
    HtmlRenderer renderer = HtmlRenderer.builder()
        .softbreak("<br/>")
        .attributeProviderFactory(context -> new HtmlAttributeProvider())
        .extensions(extensions)
        .build();
    Node document = parser.parse(content == null ? "" : content);
    return renderer.render(document);
  }

  static class HtmlAttributeProvider implements AttributeProvider {
    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
      if (node instanceof TableBlock) {
        attributes.put("class", "table table-bordered");
      }
      if (node instanceof FencedCodeBlock) {
        if (StringUtils.isEmpty(((FencedCodeBlock) node).getInfo())) {
          attributes.put("class", "nohighlight");
        }
      }
      if (node instanceof Link) {
        attributes.put("target", "_blank");
      }
    }
  }

}