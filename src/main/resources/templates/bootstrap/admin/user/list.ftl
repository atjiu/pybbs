<#include "../../common/layout.ftl"/>
<@html>
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="user"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <@spring.message "site.panel.header.admin.user.list"/>
        <span class="pull-right">${page.getTotalElements()} <@spring.message "site.panel.header.admin.user.list"/></span>
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
                <#if user.block == true>
                  <span class="text-danger"><@spring.message "site.panel.body.disable"/></span>
                <#else>
                  <span class="text-success"><@spring.message "site.panel.body.normal"/></span>
                </#if>
              </td>
              <td>
                <#if _roles?seq_contains("user:role")>
                  <a href="/admin/user/${user.id}/role" class="btn btn-xs btn-warning"><@spring.message "site.button.edit"/></a>
                </#if>
                <#if user.block == true>
                  <#if _roles?seq_contains("user:unblock")>
                    <a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.cancel"/>')) location.href='/admin/user/${user.id}/unblock'"
                       class="btn btn-xs btn-danger"><@spring.message "site.button.enable"/></a>
                  </#if>
                <#else>
                  <#if _roles?seq_contains("user:block")>
                    <a href="javascript:if(confirm('<@spring.message "site.prompt.confirm.disable"/>')) location.href='/admin/user/${user.id}/block'"
                       class="btn btn-xs btn-danger"><@spring.message "site.button.disable"/></a>
                  </#if>
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