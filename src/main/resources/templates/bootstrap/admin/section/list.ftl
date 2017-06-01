<#include "../common/layout.ftl"/>
<@html page_tab="system" page_title="板块管理">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 板块列表
        <span class="pull-right">
                    <a href="/section/add">添加板块</a>
                </span>
      </div>
      <div class="table-responsive">
        <table class="table table-striped">
          <#list sections as section>
            <tr>
              <td>${section.id!}</td>
              <td>${section.name!}</td>
              <td>${section.tab!}</td>
              <td>${section.in_time!}</td>
              <td>
                <#if section.showStatus(section) == 'true'>
                  显示
                <#else>
                  不显示
                </#if>
              </td>
              <td>
                <a href="/section/changeshowstatus?id=${section.id!}" class="btn btn-xs btn-primary">改变显示状态</a>
                <a href="/section/edit?id=${section.id!}" class="btn btn-xs btn-warning">编辑</a>
                <a href="javascript:if(confirm('确定要删除吗？')) location.href='/section/delete?id=${section.id!}'"
                   class="btn btn-xs btn-danger">删除</a>
              </td>
            </tr>
          </#list>
        </table>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>