<#include "../common/layout.ftl"/>
<@html page_title="话题管理" page_tab="admin">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="topic"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        话题管理
      </div>
      <@topics_tag p=p>
        <table class="table table-striped">
          <tbody>
            <#list page.getContent() as topic>
              <tr>
                <td>
                  <a href="/topic/${topic.id}" target="_blank">${topic.title!}</a>
                </td>
                <td>${model.formatDate(topic.inTime)}</td>
                <td>
                  <a href="javascript:if(confirm('确定要删除这个话题吗？'))location.href='/admin/topic/${topic.id!}/delete'" class="btn btn-xs btn-danger">删除</a>
                </td>
              </tr>
            </#list>
          </tbody>
        </table>
      </@topics_tag>
      <#include "../components/paginate.ftl"/>
      <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/amdin/topic/list"/>
    </div>
  </div>
</div>
</@html>