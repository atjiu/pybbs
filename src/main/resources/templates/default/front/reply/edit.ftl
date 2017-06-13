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

          <#include "../components/editor.ftl"/>
          <@editor row=15 type="reply" content=reply.content/>

          <button type="button" onclick="replySubmit()" class="btn btn-default">保存</button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
<script>
  function replySubmit() {
    var errors = 0;
    var em = $("#error_message");
    var content = $("#content").val();
    if (content.length === 0) {
      errors++;
      em.html("回复内容不能为空");
    }
    if (errors === 0) {
      var form = $("#replyForm");
      form.submit();
    }
  }
</script>
</@html>