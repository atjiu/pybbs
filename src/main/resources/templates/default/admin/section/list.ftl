<#include "../common/layout.ftl"/>
<@html page_tab="admin" page_title="板块管理">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="section"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 板块列表
        <span class="pull-right">
	        <a href="/admin/section/add">添加板块</a>
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
                <a href="/admin/section/${section.id!}/edit" class="btn btn-xs btn-warning">编辑</a>
                <a href="javascript:if(confirm('确定要删除吗？')) location.href='/admin/section/${section.id!}/delete'"
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