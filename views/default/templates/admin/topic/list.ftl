<#include "../common/layout.ftl"/>
<@html page_title="话题管理" page_tab="admin">
<div class="row">
  <div class="col-md-2 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="topic"/>
  </div>
  <div class="col-md-10">
    <div class="panel panel-default">
      <div class="panel-heading">
        话题管理
      </div>
      <@topics_tag p=p lastest=true>
        <table class="table table-striped">
          <thead>
          <tr>
            <th>标题</th>
            <th>原文</th>
            <th>R/V</th>
            <th>时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
            <#list page.getContent() as topic>
            <tr>
              <td>
                <a href="/topic/${topic.id?c}" target="_blank">${topic.title!?html}</a>
              </td>
              <td>
                <#if !model.isEmpty(topic.url)>
                  <a href="${topic.url!?html}" target="_blank">
                    <i class="glyphicon glyphicon-link"></i>
                  </a>
                </#if>
              </td>
              <td>${topic.replyCount!0}/${topic.view!0}</td>
              <td>${model.formatDate(topic.inTime)}</td>
              <td>
                <a href="/admin/topic/${topic.id?c}/edit" class="btn btn-xs btn-warning" target="_blank">编辑</a>
                <a href="javascript:if(confirm('确定要删除这个话题吗？'))location.href='/admin/topic/${topic.id?c}/delete?a=1'"
                   class="btn btn-xs btn-danger">删除</a>
              </td>
            </tr>
            </#list>
          </tbody>
        </table>
        <div class="panel-body" style="padding: 0 15px;">
          <#include "../components/paginate.ftl"/>
          <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/admin/topic/list" showdivide="no"/>
        </div>
      </@topics_tag>
    </div>
  </div>
</div>
</@html>
