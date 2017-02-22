<#include "../common/layout.ftl"/>
<@html>
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/"><@spring.message "site.panel.header.home"/></a> / <a href="/topic/${reply.topic.id}">${reply.topic.title}</a> / <@spring.message "site.panel.header.editComment"/>
      </div>
      <div class="panel-body">
        <form action="/reply/update" method="post" id="replyForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <input type="hidden" name="id" value="${reply.id!}"/>
          <input type="hidden" name="topicId" value="${reply.topic.id!}"/>
          <div class="form-group">
            <textarea name="content" id="content" rows="15" class="form-control">${reply.content?html!}</textarea>
          </div>
          <button type="button" onclick="replySubmit()" class="btn btn-default"><@spring.message "site.button.save"/></button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
<#if _editor == 'markdown'>
<link rel="stylesheet" href="/static/bootstrap/libs/editor/editor.css"/>
<script type="text/javascript" src="/static/bootstrap/libs/webuploader/webuploader.withoutimage.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/markdownit.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/editor/editor.js"></script>
<script type="text/javascript" src="/static/bootstrap/libs/editor/ext.js"></script>
<script type="text/javascript">
  var editor = new Editor({element: $("#content")[0], status: []});
  editor.render();

  var $input = $(editor.codemirror.display.input);
  $input.keydown(function (event) {
    if (event.keyCode === 13 && (event.ctrlKey || event.metaKey)) {
      event.preventDefault();
      if (editor.codemirror.getValue().length == 0) {
        $("#error_message").html(<@spring.message "site.prompt.text.commentContentCantEmpty"/>);
      } else {
        $("#replyForm").submit();
      }
    }
  });
  function replySubmit() {
    var errors = 0;
    var em = $("#error_message");
    var content = editor.value();
    if(content.length == 0) {
      errors++;
      em.html(<@spring.message "site.prompt.text.commentContentCantEmpty"/>);
    }
    if(errors == 0) {
      var form = $("#replyForm");
      form.submit();
    }
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
  function replySubmit() {
    var errors = 0;
    var em = $("#error_message");
    var content = editor.$txt.text();
    if(content.length == 0) {
      errors++;
      em.html(<@spring.message "site.prompt.text.commentContentCantEmpty"/>);
    }
    if(errors == 0) {
      var form = $("#replyForm");
      form.submit();
    }
  }
</script>
</#if>
</@html>