<#macro editor height='300px' content="">
<input type="hidden" id="commentId" value=""/>
<p class="hidden" id="replyP">回复<span id="replyAuthor"></span>: <a href="javascript:cancelReply();">取消</a></p>
<div id="editor" style="margin-bottom: 10px;">${content!}</div>
<link rel="stylesheet" href="/static/wangEditor/wangEditor.min.css">
<style>
  .w-e-text-container {height: ${height}!important;}
</style>
<script src="/static/wangEditor/wangEditor.min.js"></script>
<script type="text/javascript">
  var E = window.wangEditor;
  var editor = new E('#editor');
  editor.customConfig.uploadFileName = 'file'
  editor.customConfig.uploadImgServer = '/common/wangEditorUpload'
  editor.create();

  function cancelReply() {
    $("#replyAuthor").text("");
    $("#commentId").val("");
    $("#replyP").addClass("hidden");
  }
</script>
</#macro>