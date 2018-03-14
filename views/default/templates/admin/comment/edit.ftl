<#include "../../front/common/layout.ftl"/>
<@html page_title="${comment.topic.title!} 评论编辑">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / <a href="/topic/${comment.topic.id}">${comment.topic.title!?html}</a> / 编辑评论
      </div>
      <div class="panel-body">
        <form action="/admin/comment/update" method="post" id="editorForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <input type="hidden" name="id" value="${comment.id!}"/>
          <input type="hidden" name="topicId" value="${comment.topic.id!}"/>

          <#include "../../front/components/editor.ftl"/>
          <@editor row=15 type="comment" content=comment.content/>

          <button type="submit" class="btn btn-default">保存</button>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>