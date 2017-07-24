---
layout: post
title: 集成wangEditor富文本编辑器
---

## 下载wangEditor

目前 bootcdn.cn 上的链接打开都是404, 也不知道怎么回事，只能到github上去下载release了，下面集成demo用的是3.0.4(目前是最新的)

地址：https://github.com/wangfupeng1988/wangEditor/releases

## 集成

将下载的 tar.gz 解压，拷贝release里的文件到项目的 static 文件夹下

我只拷贝了 `wangEditor.min.css` `wangEditor.min.js` `fonts` ，你也可以拷贝全部的文件

然后将下面的代码替换 `components/editor.ftl` 里的内容, **别忘了修改里面的静态文件路径**

```html
<#macro editor row=15 type="topic" content="">
<link href="/static/default/css/wangEditor.min.css" rel="stylesheet">
<div class="form-group">
  <#if type=="topic">
    <label for="content">内容</label>
  </#if>
  <div id="div1"></div>
  <textarea name="content" id="content" class="hidden"></textarea>
</div>
<script src="/static/default/js/wangEditor.min.js"></script>
<script>
  var E = window.wangEditor;
  var editor2 = new E('#div1');
  editor2.customConfig.onchange = function (html) {
    $("#content").val(html);
  };
  editor2.create();
  editor2.txt.html('${content!}');
  $("#content").val('${content!}');
</script>
</#macro>
```

启动项目...

## 注意事项

如果你用wangEditor编辑的某些内容的某些属性在展示的时候没有了（比如样式问题），那么你可以找到 `cn.tomoya.config.base.BaseEntity` 类，将里面的 `marked` 和 `markedNotAt` 方法修改成直接返回参数即可

如果你不嫌麻烦，也可以把pegdown和jsoup这两个依赖包去掉

pegdown是将markdown文章转成html的，jsoup是过滤脚本攻击的，这两个在富文本编辑器里这个是没有用的

```java
public String marked(String content) {
  return content;
}

public String markedNotAt(String content) {
  return content;
}
```

## 对系统的影响

这里只是一个简单的demo集成，没有做图片上传的配置，也没有处理 `@` 功能，所以可能会对这两个地方有影响

关于这两个功能的集成，可以参考项目里的原来的处理逻辑

上传可以参考wangEditor的官方文档配置

`@` 功能用的是 `At.js` 源码在github上，可以参考人家的文档进行开发，本项目里处理@用户用的是正则匹配的