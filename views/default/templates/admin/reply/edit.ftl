<#include "../../front/common/layout.ftl"/>
<@html page_title="${reply.topic.title!} 回复编辑">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / <a href="/topic/${reply.topic.id}">${reply.topic.title!?html}</a> / 编辑回复
      </div>
      <div class="panel-body">
        <form action="/admin/reply/update" method="post" id="editorForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <input type="hidden" name="id" value="${reply.id!}"/>
          <input type="hidden" name="topicId" value="${reply.topic.id!}"/>

          <#include "../../front/components/editor.ftl"/>
          <@editor row=15 type="reply" content=reply.content/>

          <button type="submit" class="btn btn-default">保存</button>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>