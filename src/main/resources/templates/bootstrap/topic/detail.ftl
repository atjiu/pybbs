<#include "../common/layout.ftl">
<@html page_title="${topic.title}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body topic-detail-header">
        <div class="media">
          <div class="media-body">
            <h2 class="topic-detail-title">${topic.title!}</h2>
            <p class="gray">
              <#if topic.top == true>
                <span class="label label-primary">置顶</span>
                <span>•</span>
              <#elseif topic.good == true>
                <span class="label label-success">精华</span>
                <span>•</span>
              </#if>
              <span><a href="/user/${topic.user.username!}">${topic.user.username!}</a></span>
              <span>•</span>
              <span>${topic.formatDate(topic.inTime)}</span>
              <span>•</span>
              <span>${topic.view!1}次点击</span>
              <span>•</span>
              <span>来自 <a href="/?tab=${topic.tab!}">${topic.tab!}</a></span>
              <#if user??>
                <#if _roles?seq_contains("topic:edit")>
                  <span>•</span>
                  <span><a href="/topic/${topic.id}/edit">编辑</a></span>
                </#if>
                <#if _roles?seq_contains("topic:delete")>
                  <span>•</span>
                  <span><a
                    href="javascript:if(confirm('确定要删除吗？'))location.href='/topic/${topic.id}/delete'">删除</a></span>
                </#if>
                <#if _roles?seq_contains("topic:top")>
                  <span>•</span>
                  <#if topic.top == true>
                    <span><a href="javascript:if(confirm('确定要取消置顶吗？'))location.href='/topic/${topic.id}/top'">取消置顶</a></span>
                  <#else>
                    <span><a href="javascript:if(confirm('确定要置顶吗？'))location.href='/topic/${topic.id}/top'">置顶</a></span>
                  </#if>
                </#if>
                <#if _roles?seq_contains("topic:good")>
                  <span>•</span>
                  <#if topic.good == true>
                    <span><a href="javascript:if(confirm('确定要取消加精吗？'))location.href='/topic/${topic.id}/good'">取消加精</a></span>
                  <#else>
                    <span><a href="javascript:if(confirm('确定要加精吗？'))location.href='/topic/${topic.id}/good'">加精</a></span>
                  </#if>
                </#if>
              </#if>
            </p>
          </div>
          <div class="media-right">
            <img src="${topic.user.avatar}" class="avatar-lg"/>
          </div>
        </div>
      </div>
      <#if topic.content?? && topic.content != "">
        <div class="divide"></div>
        <div class="panel-body topic-detail-content">
        ${topic.markedNotAt(topic.content)}
        </div>
      </#if>
      <#if user??>
        <div class="panel-footer">
          <a
            href="javascript:window.open('http://service.weibo.com/share/share.php?url=${baseUrl!}/topic/${topic.id!}?r=${user.username!}&title=${topic.title!}', '_blank', 'width=550,height=370'); recordOutboundLink(this, 'Share', 'weibo.com');">分享微博</a>&nbsp;
          <#if collect??>
            <a href="/collect/${topic.id!}/delete">取消收藏</a>
          <#else>
            <a href="/collect/${topic.id!}/add">加入收藏</a>
          </#if>
          <span class="pull-right">${collectCount!0}个收藏</span>
        </div>
      </#if>
    </div>
    <#if topic.replyCount == 0>
      <div class="panel panel-default">
        <div class="panel-body text-center">目前暂无回复</div>
      </div>
    <#else>
      <div class="panel panel-default">
        <div class="panel-heading">${topic.replyCount!0} 条回复</div>
        <div class="panel-body paginate-bot">
          <#include "../components/replies.ftl"/>
          <@reply replies=replies/>
        </div>
      </div>
    </#if>
    <#if user??>
      <div class="panel panel-default">
        <div class="panel-heading">
          添加一条新回复
          <a href="javascript:;" id="goTop" class="pull-right">回到顶部</a>
        </div>
        <div class="panel-body">
          <form action="/reply/save" method="post" id="replyForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" value="${topic.id}" name="topicId"/>
            <div class="form-group">
              <textarea name="content" id="content" rows="5" class="form-control"></textarea>
            </div>
            <button type="button" onclick="replySubmit()" class="btn btn-default">回复</button>
            <span id="error_message"></span>
          </form>
        </div>
      </div>
    </#if>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/author_info.ftl"/>
        <@info/>
        <#include "../components/other_topics.ftl"/>
        <@othertopics/>
  </div>
</div>
<link rel="stylesheet" href="/static/bootstrap/css/jquery.atwho.min.css"/>
<script type="text/javascript" src="/static/bootstrap/js/jquery.atwho.min.js"></script>
<script type="text/javascript" src="/static/bootstrap/js/lodash.min.js"></script>
<link rel="stylesheet" href="/static/bootstrap/libs/editor/editor.css"/>
<style>
  .CodeMirror {
    height: 150px;
  }
</style>
<script type="text/javascript" src="/static/bootstrap/js/highlight.min.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/webuploader/webuploader.withoutimage.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/markdownit.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/editor/editor.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/editor/ext.js"></script>
<script type="text/javascript">

  $('pre code').each(function (i, block) {
    hljs.highlightBlock(block);
  });

  var editor = new Editor({element: $("#content")[0], status: []});
  editor.render();

  var $input = $(editor.codemirror.display.input);
  $input.keydown(function (event) {
    if (event.keyCode === 13 && (event.ctrlKey || event.metaKey)) {
      event.preventDefault();
      if (editor.codemirror.getValue().length == 0) {
        $("#error_message").html("回复内容不能为空");
      } else {
        $("#replyForm").submit();
      }
    }
  });

  // at.js 配置
  var codeMirrorGoLineUp = CodeMirror.commands.goLineUp;
  var codeMirrorGoLineDown = CodeMirror.commands.goLineDown;
  var codeMirrorNewlineAndIndent = CodeMirror.commands.newlineAndIndent;
  var data = [];
    <#list replies as reply>
    data.push('${reply.user.username}');
    </#list>
  data = _.unique(data);
  $input.atwho({
    at: "@",
    data: data
  }).on('shown.atwho', function () {
    CodeMirror.commands.goLineUp = _.noop;
    CodeMirror.commands.goLineDown = _.noop;
    CodeMirror.commands.newlineAndIndent = _.noop;
  })
    .on('hidden.atwho', function () {
      CodeMirror.commands.goLineUp = codeMirrorGoLineUp;
      CodeMirror.commands.goLineDown = codeMirrorGoLineDown;
      CodeMirror.commands.newlineAndIndent = codeMirrorNewlineAndIndent;
    });
  // END at.js 配置
</script>
</@html>