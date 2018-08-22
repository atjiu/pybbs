<#include "../layout/" + layoutName>
<@html page_title="编辑评论">
<div class="row">
  <div class="panel panel-default">
    <div class="panel-heading">
      <a data-pjax href="/">主页</a> / <a href="/topic/${topic.id}">${topic.title}</a> / 编辑评论
    </div>
    <div class="panel-body">
      <form id="form">
        <div class="form-group">
          <label for="content">内容</label>
          <#include "../components/editor.ftl"/>
          <@editor height="400px" content=comment.content/>
        </div>
        <button type="button" id="btn" class="btn btn-default">
          <span class="glyphicon glyphicon-send"></span> 更新
        </button>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(function() {
    $("#btn").click(function() {
      var contentHtml = editor.txt.html();
      var contentText = editor.txt.text();
      if (!contentText) {
        toast("请输入内容");
      } else {
        $.ajax({
          url: '/api/comment/edit',
          type: 'post',
          async: false,
          cache: false,
          dataType: 'json',
          data: {
            id: '${comment.id}',
            content: contentHtml
          },
          success: function(data) {
            if (data.code === 200) {
              window.location.href = "/topic/${topic.id}";
            } else {
              toast(data.description);
            }
          }
        })
      }
    });
  })
</script>
</@html>