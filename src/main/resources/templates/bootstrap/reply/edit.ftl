<#include "../common/layout.ftl"/>
<@html page_title="${reply.topic.title} 回复编辑">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / <a href="/topic/${reply.topic.id}">${reply.topic.title}</a> / 编辑回复
      </div>
      <div class="panel-body">
        <form action="/reply/update" method="post" id="replyForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <input type="hidden" name="id" value="${reply.id!}"/>
          <input type="hidden" name="topicId" value="${reply.topic.id!}"/>
          <div class="form-group">
            <textarea name="content" id="content" rows="15" class="form-control">${reply.content?html!}</textarea>
          </div>
          <button type="button" onclick="replySubmit()" class="btn btn-default">保存</button>
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
        $("#error_message").html("回复内容不能为空");
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
      em.html("回复内容不能为空");
    }
    if(errors == 0) {
      var form = $("#replyForm");
      form.submit();
    }
  }
</script>
<#elseif _editor == 'wangeditor'>
<script type="text/javascript" src="/static/bootstrap/js/jquery.atwho.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/wangeditor/2.1.20/css/wangEditor.min.css">
<script src="//cdn.bootcss.com/wangeditor/2.1.20/js/wangEditor.min.js"></script>
<script>
  var editor = new wangEditor('content');
  // 普通的自定义菜单
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
  // 上传图片（举例）
  editor.config.uploadImgUrl = '/wangEditorUpload';
  // 配置自定义参数（举例）
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
      em.html("回复内容不能为空");
    }
    if(errors == 0) {
      var form = $("#replyForm");
      form.submit();
    }
  }
</script>
</#if>
</@html>