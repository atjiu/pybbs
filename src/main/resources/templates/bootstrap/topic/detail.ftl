<#include "../common/layout.ftl">
<@html>
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body topic-detail-header">
        <div class="media">
          <div class="media-body">
            <h2 class="topic-detail-title">${topic.title!}</h2>
            <p class="gray">
              <#if topic.top == true>
                <span class="label label-primary"><@spring.message "site.panel.body.top"/></span>
                <span>•</span>
              <#elseif topic.good == true>
                <span class="label label-success"><@spring.message "site.panel.body.good"/></span>
                <span>•</span>
              </#if>
              <span><a href="/user/${topic.user.username!}">${topic.user.username!}</a></span>
              <span>•</span>
              <span>${topic.formatDate(topic.inTime)}</span>
              <span>•</span>
              <span>${topic.view!1}<@spring.message "site.panel.body.views"/></span>
              <span>•</span>
              <span><@spring.message "site.panel.body.from"/> <a href="/?tab=${topic.tab!}">${topic.tab!}</a></span>
              <#if user?? && user.block == false>
                <#if _roles?seq_contains("topic:edit") || user.id == topic.user.id>
                  <span>•</span>
                  <span><a href="/topic/${topic.id}/edit"><@spring.message "site.button.edit"/></a></span>
                </#if>
                <#if _roles?seq_contains("topic:delete")  || user.id == topic.user.id>
                  <span>•</span>
                  <span><a
                    href="javascript:if(confirm('<@spring.message "site.prompt.confirm.delete"/>'))location.href='/topic/${topic.id}/delete'"><@spring.message "site.button.delete"/></a></span>
                </#if>
                <#if _roles?seq_contains("topic:top")>
                  <span>•</span>
                  <#if topic.top == true>
                    <span><a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.cancel"/>'))location.href='/topic/${topic.id}/top'"><@spring.message "site.panel.body.cancelTop"/></a></span>
                  <#else>
                    <span><a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.top"/>'))location.href='/topic/${topic.id}/top'"><@spring.message "site.panel.body.top"/></a></span>
                  </#if>
                </#if>
                <#if _roles?seq_contains("topic:good")>
                  <span>•</span>
                  <#if topic.good == true>
                    <span><a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.cancel"/>'))location.href='/topic/${topic.id}/good'"><@spring.message "site.panel.body.cancelGood"/></a></span>
                  <#else>
                    <span><a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.good"/>'))location.href='/topic/${topic.id}/good'"><@spring.message "site.panel.body.good"/></a></span>
                  </#if>
                </#if>

                <span>•</span>
                <#if topic.lock == true>
                  <span><a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.cancel"/>'))location.href='/topic/${topic.id}/lock'"><@spring.message "site.panel.body.cancelLock"/></a></span>
                <#else>
                  <span><a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.lock"/>'))location.href='/topic/${topic.id}/lock'"><@spring.message "site.panel.body.lock"/></a></span>
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
          <#if topic.editor?? && topic.editor == 'markdown'>
            ${topic.markedNotAt(topic.content)}
          <#elseif topic.editor?? && topic.editor == 'wangeditor'>
            ${topic.content!}
          </#if>
        </div>
      </#if>
      <#if user??>
        <div class="panel-footer">
          <a href="javascript:window.open('http://service.weibo.com/share/share.php?url=${baseUrl!}/topic/${topic.id!}?r=${user.username!}&title=${topic.title!}', '_blank', 'width=550,height=370'); recordOutboundLink(this, 'Share', 'weibo.com');"><@spring.message "site.panel.body.shareWeibo"/></a>&nbsp;
          <#if collect??>
            <a href="/collect/${topic.id!}/delete"><@spring.message "site.panel.body.cancelCollection"/></a>
          <#else>
            <a href="/collect/${topic.id!}/add"><@spring.message "site.panel.body.collection"/></a>
          </#if>
          <span class="pull-right">${collectCount!0} <@spring.message "site.panel.body.collection"/></span>
        </div>
      </#if>
    </div>

    <#if topic.replyCount == 0 && topic.lock == false>
      <div class="panel panel-default">
        <div class="panel-body text-center"><@spring.message "site.panel.body.noComment"/></div>
      </div>
    <#elseif topic.replyCount &gt; 0>
      <div class="panel panel-default">
        <div class="panel-heading">${topic.replyCount!0} <@spring.message "site.panel.body.comment"/></div>
        <div class="panel-body paginate-bot panel-body-reply">
          <#include "../components/replies.ftl"/>
          <@reply replies=replies/>
        </div>
      </div>
    </#if>

    <#--topic lock-->
    <#if topic.lock == true>
      <div class="panel panel-default">
        <div class="panel-body text-center"><@spring.message "site.panel.body.lockCantComment"/></div>
      </div>
    </#if>

    <#--user disable-->
    <#if user?? && user.block == true>
      <div class="panel panel-default">
        <div class="panel-body text-center"><@spring.message "site.panel.body.accountDisabled"/></div>
      </div>
    </#if>

    <#--add comment-->
    <#if user?? && user.block == false && topic.lock == false>
      <div class="panel panel-default">
        <div class="panel-heading">
          <@spring.message "site.panel.body.addComment"/>
          <a href="javascript:;" id="goTop" class="pull-right"><@spring.message "site.panel.body.backToTop"/></a>
        </div>
        <div class="panel-body">
          <form action="/reply/save" method="post" id="replyForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" value="${topic.id}" name="topicId"/>
            <div class="form-group">
              <textarea name="content" id="content" rows="5" class="form-control" style="height: 150px;"></textarea>
            </div>
            <button type="button" onclick="replySubmit()" class="btn btn-default"><@spring.message "site.panel.body.comment"/></button>
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
<#if user?? && user.block == false>
  <link rel="stylesheet" href="/static/bootstrap/css/jquery.atwho.min.css"/>
  <script type="text/javascript" src="/static/bootstrap/js/lodash.min.js"></script>
  <script>
    var data = [];
      <#list replies as reply>
      data.push('${reply.user.username}');
      </#list>
    data = _.unique(data);
  </script>
  <#if _editor == 'markdown'>
    <link rel="stylesheet" href="/static/bootstrap/libs/editor/editor.css"/>
    <script type="text/javascript" src="/static/bootstrap/js/jquery.atwho.markdown.min.js"></script>
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
            $("#error_message").html("<@spring.message "site.prompt.text.commentContentCantEmpty"/>");
          } else {
            $("#replyForm").submit();
          }
        }
      });

      // at.js configuration
      var codeMirrorGoLineUp = CodeMirror.commands.goLineUp;
      var codeMirrorGoLineDown = CodeMirror.commands.goLineDown;
      var codeMirrorNewlineAndIndent = CodeMirror.commands.newlineAndIndent;

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
      // END at.js configuration
      function replySubmit() {
        var errors = 0;
        var em = $("#error_message");
        var content = editor.value();
        if(content.length == 0) {
          errors++;
          em.html("<@spring.message "site.prompt.text.commentContentCantEmpty"/>");
        }
        if(errors == 0) {
          var form = $("#replyForm");
          form.submit();
        }
      }
      function replythis(author) {
        var content = $(editor.codemirror.display.input);
        var oldContent = editor.value();
        var prefix = "@" + author + " ";
        var newContent = '';
        if(oldContent.length > 0){
          if (oldContent != prefix) {
            newContent = oldContent + '\n' + prefix;
          }
        } else {
          newContent = prefix
        }
        editor.value(newContent);
        CodeMirror.commands.goDocEnd(editor.codemirror);
        content.focus();
        moveEnd(content);
      }
    </script>
  <#elseif _editor == 'wangeditor'>
    <script type="text/javascript" src="/static/bootstrap/js/jquery.atwho.min.js"></script>
    <link rel="stylesheet" href="/static/bootstrap/libs/wangeditor/css/wangEditor.min.css">
    <script src="/static/bootstrap/libs/wangeditor/js/wangEditor.min.js"></script>
    <script>
      var editor = new wangEditor('content');
      editor.config.menus = [
        'source',
        '|',
        'bold',
        'underline',
        'italic',
        'strikethrough',
        'forecolor',
        'bgcolor',
        '|',
        'quote',
        'fontfamily',
        'fontsize',
        'head',
        'unorderlist',
        'orderlist',
        '|',
        'link',
        'unlink',
        'table',
        '|',
        'img',
        'insertcode'
      ];
      editor.config.uploadImgUrl = '/wangEditorUpload';
      editor.config.uploadParams = {
        '${_csrf.parameterName}': '${_csrf.token}'
      };
      editor.config.uploadImgFileName = 'file';
      editor.create();
      editor.$txt.atwho({
        at: "@",
        data: data
      });
      function replySubmit() {
        var errors = 0;
        var em = $("#error_message");
        var content = editor.$txt.text();
        if(content.length == 0 && editor.$txt.html().indexOf('<img') == -1) {
          errors++;
          em.html("<@spring.message "site.prompt.text.commentContentCantEmpty"/>");
        }
        if(errors == 0) {
          var form = $("#replyForm");
          form.submit();
        }
      }
      function replythis(author) {
        editor.$txt.append("@" + author + " ");
      }
    </script>
  </#if>
</#if>
</@html>