package co.yiiu.core.util;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TableHead;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class MarkdownUtil {

//  private final static PegDownProcessor md = new PegDownProcessor(
//      Extensions.ALL_OPTIONALS | Extensions.ALL_WITH_OPTIONALS);
//
//  public static String pegDown(String content) {
//    return md.markdownToHtml(content == null ? "" : content);
//  }

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
        .attributeProviderFactory(context -> new ImageAttributeProvider())
        .extensions(extensions)
        .build();
    Node document = parser.parse(content == null ? "" : content);
    return renderer.render(document);
  }

  static class ImageAttributeProvider implements AttributeProvider {
    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
//      if (node instanceof Image) {
//        attributes.put("class", "border");
//      }
      if (node instanceof TableBlock) {
        attributes.put("class", "table table-bordered");
      }
      if (node instanceof FencedCodeBlock) {
        if (StringUtils.isEmpty(((FencedCodeBlock) node).getInfo())) {
          attributes.put("class", "nohighlight");
        }
      }
    }
  }

}