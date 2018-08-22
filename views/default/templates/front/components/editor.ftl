<#macro editor height='300px' content="">
<input type="hidden" id="commentId" value=""/>
<p class="hidden" id="replyP">回复<span id="replyAuthor"></span>: <a data-pjax href="javascript:cancelReply();">取消</a></p>
<div id="editor" style="margin-bottom: 10px;"><div>${content!}</div></div>
<link rel="stylesheet" href="/static/wangEditor/wangEditor.min.css">
<style>
  .w-e-text-container {height: ${height}!important;}
</style>
<script src="/static/wangEditor/wangEditor.min.js"></script>
<script type="text/javascript">
  var E = window.wangEditor;
  var editor = new E('#editor');
  editor.customConfig.uploadFileName = 'file';
  editor.customConfig.uploadImgServer = '/api/common/wangEditorUpload';
  editor.customConfig.menus = [
    'head',  // 标题
    'bold',  // 粗体
    'italic',  // 斜体
    'underline',  // 下划线
    'strikeThrough',  // 删除线
    'link',  // 插入链接
    'list',  // 列表
    'quote',  // 引用
    'emoticon',  // 表情
    'image',  // 插入图片
    'table',  // 表格
    'code',  // 插入代码
    'undo',  // 撤销
    'redo'  // 重复
  ];
  editor.create();

  function commentThis(username, commentId) {
    $("#replyAuthor").text(username);
    $("#commentId").val(commentId);
    $("#replyP").removeClass("hidden");
  }

  function cancelReply() {
    $("#replyAuthor").text("");
    $("#commentId").val("");
    $("#replyP").addClass("hidden");
  }
</script>
</#macro>