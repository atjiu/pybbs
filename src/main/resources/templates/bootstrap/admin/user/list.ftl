<#include "../../common/layout.ftl"/>
<@html page_title="用户管理">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="user"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        用户管理
        <span class="pull-right">${page.getTotalElements()}个用户</span>
      </div>
      <div class="table-responsive">
        <table class="table table-striped">
          <tbody>
            <#list page.getContent() as user>
            <tr>
              <td>${user.id}</td>
              <td><a href="/user/${user.username}" target="_blank">${user.username}</a></td>
              <td><a href="mailto:${user.email!}" target="_blank">${user.email!}</a></td>
              <td><a href="${user.url!}" target="_blank">${user.url!}</a></td>
              <td>
                <#if _roles?seq_contains("user:role")>
                  <a href="/admin/user/${user.id}/role" class="btn btn-xs btn-warning">配置角色</a>
                </#if>
                <#if _roles?seq_contains("user:delete")>
                  <a href="javascript:if(confirm('确认删除吗?')) location.href='/admin/user/${user.id}/delete'"
                     class="btn btn-xs btn-danger">删除</a>
                </#if>
              </td>
            </tr>
            </#list>
          </tbody>
        </table>
      </div>
      <div class="panel-body" style="padding: 0 15px;">
        <#include "../../components/paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/admin/user/list" urlParas="" showdivide="no"/>
      </div>
    </div>
  </div>
</div>
</@html>