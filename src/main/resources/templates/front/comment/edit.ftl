<#include "../layout/layout.ftl"/>
<@html page_title="编辑话题" page_tab="">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / <a href="/topic/${topic.id}">${topic.title}</a> / 编辑评论
      </div>
      <textarea name="content" id="content" class="form-control">${comment.content?html}</textarea>
      <div class="panel-body">
        <button type="button" id="btn" class="btn btn-default">
          <span class="glyphicon glyphicon-send"></span> 更新
        </button>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <#include "../components/markdown_guide.ftl"/>
    <#include "../components/create_topic_guide.ftl"/>
  </div>
</div>
<link href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.38.0/codemirror.min.css" rel="stylesheet">
<style>
  .CodeMirror {
    border-top: 0;
  }
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.38.0/codemirror.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.38.0/mode/markdown/markdown.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.38.0/addon/display/placeholder.min.js"></script>
<script>
  $(function () {
    CodeMirror.keyMap.default["Shift-Tab"] = "indentLess";
    CodeMirror.keyMap.default["Tab"] = "indentMore";
    var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
      lineNumbers: true,     // 显示行数
      indentUnit: 4,         // 缩进单位为4
      tabSize: 4,
      matchBrackets: true,   // 括号匹配
      mode: 'markdown',     // Markdown模式
      lineWrapping: true,    // 自动换行
    });

    $("#btn").click(function () {
      var content = editor.getDoc().getValue();
      if (!content) {
        toast("请输入内容");
        return ;
      }
      $.post("/api/comment/update", {
        id: ${comment.id},
        content: content
      }, function (data) {
        if (data.code === 200) {
          toast("更新成功", "success");
          setTimeout(function () {
            window.location.href = "/topic/${comment.topicId}";
          }, 700);
        } else {
          toast(data.description);
        }
      })
    })
  });
</script>
</@html>
